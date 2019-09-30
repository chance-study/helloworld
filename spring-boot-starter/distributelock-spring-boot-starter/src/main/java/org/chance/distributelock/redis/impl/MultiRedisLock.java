package org.chance.distributelock.redis.impl;

import org.chance.distributelock.api.Lock;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.util.StringUtils;

/**
 * MultiRedisLock
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/24
 */
public class MultiRedisLock implements Lock {

    private static final Logger log = LoggerFactory
        .getLogger(MultiRedisLock.class);

    private static final String LOCK_SCRIPT = "local msetnx_keys_with_tokens = {}\n" +
            "for _, key in ipairs(KEYS) do\n" +
            "    msetnx_keys_with_tokens[#msetnx_keys_with_tokens + 1] = key\n" +
            "    msetnx_keys_with_tokens[#msetnx_keys_with_tokens + 1] = ARGV[1]\n" +
            "end\n" +
            "local keys_successfully_set = redis.call('MSETNX', unpack(msetnx_keys_with_tokens))\n" +
            "if (keys_successfully_set == 0) then\n" +
            "    return false\n" +
            "end\n" +
            "local expiration = tonumber(ARGV[2])\n" +
            "for _, key in ipairs(KEYS) do\n" +
            "    redis.call('PEXPIRE', key, expiration)\n" +
            "end\n" +
            "return true\n";

    private static final String LOCK_RELEASE_SCRIPT = "for _, key in pairs(KEYS) do\n" +
            "    if redis.call('GET', key) ~= ARGV[1] then\n" +
            "        return false\n" +
            "    end\n" +
            "end\n" +
            "redis.call('DEL', unpack(KEYS))\n" +
            "return true\n";

    private static final String LOCK_REFRESH_SCRIPT = "for _, key in pairs(KEYS) do\n" +
            "    local value = redis.call('GET', key)\n" +
            "    if (value == nil or value ~= ARGV[1]) then\n" +
            "        return false\n" +
            "    end\n" +
            "end\n" +
            "for _, key in pairs(KEYS) do\n" +
            "    redis.call('PEXPIRE', key, ARGV[2])\n" +
            "end\n" +
            "return true";

    private final RedisScript<Boolean> lockScript = new DefaultRedisScript<>(LOCK_SCRIPT, Boolean.class);
    private final RedisScript<Boolean> lockReleaseScript = new DefaultRedisScript<>(LOCK_RELEASE_SCRIPT, Boolean.class);
    private final RedisScript<Boolean> lockRefreshScript = new DefaultRedisScript<>(LOCK_REFRESH_SCRIPT, Boolean.class);

    private final StringRedisTemplate stringRedisTemplate;
    private final Supplier<String> tokenSupplier;

    public MultiRedisLock(final StringRedisTemplate stringRedisTemplate) {
        this(stringRedisTemplate, () -> UUID.randomUUID().toString());
    }

    public MultiRedisLock(StringRedisTemplate stringRedisTemplate, Supplier<String> tokenSupplier) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.tokenSupplier = tokenSupplier;
    }

    @Override
    public String acquire(final List<String> keys, final String storeId, final long expiration) {
        final List<String> keysWithStoreIdPrefix = keys.stream().map(key -> storeId + ":" + key).collect(Collectors.toList());
        final String token = tokenSupplier.get();

        if (StringUtils.isEmpty(token)) {
            throw new IllegalStateException("Cannot lock with empty token");
        }

        final Boolean locked = stringRedisTemplate.execute(lockScript, keysWithStoreIdPrefix, token, String.valueOf(expiration));
        log.debug("Tried to acquire lock for keys {} in store {} with token {}. Locked: {}", keys, storeId, token, locked);
        return locked ? token : null;
    }

    @Override
    public boolean release(final List<String> keys, final String storeId, final String token) {
        final List<String> keysWithStoreIdPrefix = keys.stream().map(key -> storeId + ":" + key).collect(Collectors.toList());

        final boolean released = stringRedisTemplate.execute(lockReleaseScript, keysWithStoreIdPrefix, token);
        if (released) {
            log.debug("Release script deleted the record for keys {} with token {} in store {}", keys, token, storeId);
        } else {
            log.error("Release script failed for keys {} with token {} in store {}", keys, token, storeId);
        }
        return released;
    }

    @Override
    public boolean refresh(final List<String> keys, final String storeId, final String token, final long expiration) {
        final List<String> keysWithStoreIdPrefix = keys.stream().map(key -> storeId + ":" + key).collect(Collectors.toList());

        final boolean refreshed = stringRedisTemplate.execute(lockRefreshScript, keysWithStoreIdPrefix, token, String.valueOf(expiration));
        if (refreshed) {
            log.debug("Refresh script refreshed the expiration for keys {} with token {} in store {}", keys, token, storeId);
        } else {
            log.debug("Refresh script failed to update expiration for keys {} with token {} in store {}", keys, token, storeId);
        }
        return refreshed;
    }

}
