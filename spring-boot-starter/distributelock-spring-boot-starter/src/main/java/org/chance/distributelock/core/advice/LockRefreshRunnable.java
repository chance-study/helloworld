package org.chance.distributelock.core.advice;

import org.chance.distributelock.api.Lock;

import java.util.List;

/**
 * LockRefreshRunnable
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
public class LockRefreshRunnable implements Runnable {

    private final Lock lock;
    private final List<String> keys;
    private final String storeId;
    private final String token;
    private final long expiration;

    public LockRefreshRunnable(Lock lock, List<String> keys, String storeId, String token, long expiration) {
        this.lock = lock;
        this.keys = keys;
        this.storeId = storeId;
        this.token = token;
        this.expiration = expiration;
    }

    @Override
    public void run() {
        lock.refresh(keys, storeId, token, expiration);
    }
}
