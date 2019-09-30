package org.chance.distributelock.redis.impl;

import org.chance.distributelock.api.Lock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * SimpleRedisLock
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
public class SimpleRedisLock implements Lock {

    private static final Logger log = LoggerFactory
        .getLogger(SimpleRedisLock.class);

    private static final String LOCK_SCRIPT = "return redis.call('SET', KEYS[1], ARGV[1], 'PX', tonumber(ARGV[2]), 'NX') and true or false";

    private static final String LOCK_RELEASE_SCRIPT = "return redis.call('GET', KEYS[1]) == ARGV[1] and (redis.call('DEL', KEYS[1]) == 1) or false";

    private static final String LOCK_REFRESH_SCRIPT = "if redis.call('GET', KEYS[1]) == ARGV[1] then\n" +
            "    redis.call('PEXPIRE', KEYS[1], tonumber(ARGV[2]))\n" +
            "    return true\n" +
            "end\n" +
            "return false";

    private final RedisScript<Boolean> lockScript = new DefaultRedisScript<>(LOCK_SCRIPT, Boolean.class);
    private final RedisScript<Boolean> lockReleaseScript = new DefaultRedisScript<>(LOCK_RELEASE_SCRIPT, Boolean.class);
    private final RedisScript<Boolean> lockRefreshScript = new DefaultRedisScript<>(LOCK_REFRESH_SCRIPT, Boolean.class);

    private final StringRedisTemplate stringRedisTemplate;
    private final Supplier<String> tokenSupplier;

    public SimpleRedisLock(final StringRedisTemplate stringRedisTemplate) {
        this(stringRedisTemplate, () -> UUID.randomUUID().toString());
    }

    public SimpleRedisLock(StringRedisTemplate stringRedisTemplate, Supplier<String> tokenSupplier) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.tokenSupplier = tokenSupplier;
    }

    @Override
    public String acquire(final List<String> keys, final String storeId, final long expiration) {
        Assert.isTrue(keys.size() == 1, "Cannot acquire lock for multiple keys with this lock: " + keys);
        final String key = keys.get(0);
        final List<String> singletonKeyList = Collections.singletonList(storeId + ":" + key);

        final String token = tokenSupplier.get();

        if (StringUtils.isEmpty(token)) {
            throw new IllegalStateException("Cannot lock with empty token");
        }

        final boolean locked = stringRedisTemplate.execute(lockScript, singletonKeyList, token, String.valueOf(expiration));
        log.debug("Tried to acquire lock for key {} with token {} in store {}. Locked: {}", key, token, storeId, locked);
        return locked ? token : null;
    }

    @Override
    public boolean release(final List<String> keys, final String storeId, final String token) {
        Assert.isTrue(keys.size() == 1, "Cannot release lock for multiple keys with this lock: " + keys);
        final String key = keys.get(0);
        final List<String> singletonKeyList = Collections.singletonList(storeId + ":" + key);

        final boolean released = stringRedisTemplate.execute(lockReleaseScript, singletonKeyList, token);
        if (released) {
            log.debug("Release script deleted the record for key {} with token {} in store {}", key, token, storeId);
        } else {
            log.error("Release script failed for key {} with token {} in store {}", key, token, storeId);
        }
        return released;
    }

    @Override
    public boolean refresh(final List<String> keys, final String storeId, final String token, final long expiration) {
        Assert.isTrue(keys.size() == 1, "Cannot refresh lock for multiple keys with this lock: " + keys);
        final String key = keys.get(0);
        final List<String> singletonKeyList = Collections.singletonList(storeId + ":" + key);

        final boolean refreshed = stringRedisTemplate.execute(lockRefreshScript, singletonKeyList, token, String.valueOf(expiration));
        if (refreshed) {
            log.debug("Refresh script updated the expiration for key {} with token {} in store {} to {}", key, token, storeId, expiration);
        } else {
            log.debug("Refresh script failed to update expiration for key {} with token {} in store {} with expiration: {}", key, token, storeId, expiration);
        }
        return refreshed;
    }
}