package org.chance.distributelock.zookeeper.impl;

import org.apache.curator.framework.CuratorFramework;
import org.chance.distributelock.api.Lock;
import org.chance.distributelock.embedded.EmbeddedZooKeeper;
import org.chance.distributelock.zookeeper.configuration.ZkDistributedLockConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ZookeeperLockTest implements InitializingBean {

    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private Lock lock;

    @Override
    public void afterPropertiesSet() {

        EmbeddedZooKeeper embeddedZooKeeper = new EmbeddedZooKeeper(2181, false);

        embeddedZooKeeper.start();

        lock = new ZookeeperLock(curatorFramework, () -> "abc");

    }

    @Before
    public void cleanRedis() {
    }

    @Test
    public void shouldLock() {
        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(token).isEqualTo("abc");

//        assertThat(curatorFramework.getData()).isEqualTo("abc");
    }

    @SpringBootApplication(scanBasePackageClasses = {ZkDistributedLockConfiguration.class, EmbeddedZooKeeper.class})
    static class TestApplication {

    }
}