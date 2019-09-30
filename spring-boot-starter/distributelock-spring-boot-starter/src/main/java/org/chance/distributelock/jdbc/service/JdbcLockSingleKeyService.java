package org.chance.distributelock.jdbc.service;

public interface JdbcLockSingleKeyService {
    String acquire(String key, String storeId, String token, long expiration);

    boolean release(String key, String storeId, String token);

    boolean refresh(String key, String storeId, String token, long expiration);
}
