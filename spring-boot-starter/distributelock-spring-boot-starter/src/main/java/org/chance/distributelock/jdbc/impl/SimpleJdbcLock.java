package org.chance.distributelock.jdbc.impl;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.chance.distributelock.api.Lock;
import org.chance.distributelock.jdbc.service.JdbcLockSingleKeyService;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

public class SimpleJdbcLock implements Lock {

    private final JdbcLockSingleKeyService lockSingleKeyService;
    private final Supplier<String> tokenSupplier;

    public SimpleJdbcLock(final JdbcLockSingleKeyService lockSingleKeyService) {
        this(lockSingleKeyService, () -> UUID.randomUUID().toString());
    }

    public SimpleJdbcLock(JdbcLockSingleKeyService lockSingleKeyService, Supplier<String> tokenSupplier) {
        this.lockSingleKeyService = lockSingleKeyService;
        this.tokenSupplier = tokenSupplier;
    }

    @Override
    public String acquire(final List<String> keys, final String storeId, final long expiration) {
        Assert.isTrue(keys.size() == 1, "Cannot acquire lock for multiple keys with this lock: " + keys);

        final String key = keys.get(0);
        final String token = tokenSupplier.get();

        if (StringUtils.isEmpty(token)) {
            throw new IllegalStateException("Cannot lock with empty token");
        }

        return lockSingleKeyService.acquire(key, storeId, token, expiration);
    }

    @Override
    public boolean release(final List<String> keys, final String storeId, final String token) {
        Assert.isTrue(keys.size() == 1, "Cannot release lock for multiple keys with this lock: " + keys);
        return lockSingleKeyService.release(keys.get(0), storeId, token);
    }

    @Override
    public boolean refresh(final List<String> keys, final String storeId, final String token, final long expiration) {
        Assert.isTrue(keys.size() == 1, "Cannot refresh lock for multiple keys with this lock: " + keys);
        return lockSingleKeyService.refresh(keys.get(0), storeId, token, expiration);
    }
}
