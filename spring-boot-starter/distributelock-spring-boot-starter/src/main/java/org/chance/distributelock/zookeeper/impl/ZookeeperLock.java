package org.chance.distributelock.zookeeper.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.chance.distributelock.api.Lock;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

@Slf4j
public class ZookeeperLock implements Lock {

    /**
     * 分布式锁根节点
     */
    private String ROOT_LOCK = "/locks";

    /**
     * curatorFramework
     */
    private CuratorFramework curatorFramework;

    private final Supplier<String> tokenSupplier;

    public ZookeeperLock(final CuratorFramework curatorFramework) {
        this(curatorFramework, () -> UUID.randomUUID().toString());
    }

    public ZookeeperLock(final CuratorFramework curatorFramework, Supplier<String> tokenSupplier) {
        this.curatorFramework = curatorFramework;
        this.tokenSupplier = tokenSupplier;
        initLockRootPath();
    }

    private void initLockRootPath() {
        CuratorFrameworkState state = curatorFramework.getState();
        if(state.equals(CuratorFrameworkState.LATENT)) {
            curatorFramework.start();
        }
    }

    @Override
    public String acquire(List<String> keys, String storeId, long expiration) {

        Assert.isTrue(keys.size() == 1, "Cannot acquire lock for multiple keys with this lock: " + keys);

        final String key = keys.get(0);

        final String keyNode = storeId + ":" + key;

        final String token = tokenSupplier.get();

        if (StringUtils.isEmpty(token)) {
            throw new IllegalStateException("Cannot lock with empty token");
        }

        // 尝试获取锁
        // 创建分布式锁, 锁空间的根节点路径
        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, ROOT_LOCK + "/" + keyNode);

        boolean locked = false;

        try {
            mutex.acquire();
            locked = true;
        } catch (Exception e) {
            log.error("", e);
            locked = false;
        }

        log.debug("Tried to acquire lock for key {} with token {} in store {}. Locked: {}", key, token, storeId, locked);
        return locked ? token : null;

    }

    @Override
    public boolean release(List<String> keys, String storeId, String token) {

        Assert.isTrue(keys.size() == 1, "Cannot release lock for multiple keys with this lock: " + keys);

        final String key = keys.get(0);

        final String keyNode = storeId + ":" + key;

        // 释放锁，删除节点
        boolean released = true;

        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, ROOT_LOCK + "/" + keyNode);

        try {
            mutex.release();
        } catch (Exception e) {
            log.error("{}", e);
            released = false;
        }

        if (released) {
            log.debug("Release script deleted the record for key {} with token {} in store {}", key, token, storeId);
        } else {
            log.error("Release script failed for key {} with token {} in store {}", key, token, storeId);
        }

        return released;

    }

    @Override
    public boolean refresh(List<String> keys, String storeId, String token, long expiration) {
        return false;
    }

}
