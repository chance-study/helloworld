package org.chance.spring.feature.zookeeper;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.chance.spring.feature.zookeeper.embedded.EmbeddedZooKeeper;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;


@DirtiesContext
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = ZookeeperApplicationTest.TestApplication.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@Slf4j
public class ZookeeperApplicationTest implements InitializingBean {

    EmbeddedZooKeeper embeddedZooKeeper;

    @Autowired
    private CuratorFramework curatorFramework;

    @After
    public void after() {
        embeddedZooKeeper.stop();
    }

    @Test
    public void test() throws Exception {

        curatorFramework.getNamespace();

        curatorFramework.create().withMode(CreateMode.EPHEMERAL).forPath("/head");

        String data =  curatorFramework.getData().forPath("/head").toString();

    }

    @Override
    public void afterPropertiesSet() throws Exception {
        embeddedZooKeeper = new EmbeddedZooKeeper(2181, false);
        embeddedZooKeeper.start();
    }

    @SpringBootApplication(scanBasePackageClasses = {ZkDistributedLockConfiguration.class, EmbeddedZooKeeper.class})
    static class TestApplication {

    }

}