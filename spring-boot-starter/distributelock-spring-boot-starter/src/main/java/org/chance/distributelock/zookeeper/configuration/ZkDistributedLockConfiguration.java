package org.chance.distributelock.zookeeper.configuration;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.CompressionProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZookeeperFactory;
import org.chance.distributelock.zookeeper.impl.ZookeeperLock;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * RedisDistributedLockConfiguration
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/24
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CuratorProperties.class)
public class ZkDistributedLockConfiguration implements BeanFactoryAware {

    @Autowired
    private CuratorProperties curatorProperties;

    private BeanFactory beanFactory;


    @Bean
    @ConditionalOnMissingBean(RetryPolicy.class)
    public RetryPolicy retryPolicy() {
        return new ExponentialBackoffRetry(curatorProperties.getBaseSleepTimeMs(), curatorProperties.getMaxRetries());
    }

    @Bean(initMethod = "start", destroyMethod = "close")
    @ConditionalOnMissingBean(CuratorFramework.class)
    public CuratorFramework curatorFramework(RetryPolicy retryPolicy) {


        final CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder();

        // IMPORTANT: use either connection-string or ensembleProvider but not both.
        if (StringUtils.hasText(curatorProperties.getConnectString())) {
            // connection string will be first
            builder.connectString(curatorProperties.getConnectString());
        } else if (StringUtils.hasLength(curatorProperties.getEnsembleProviderRef())) {
            builder.ensembleProvider(beanFactory.getBean(curatorProperties.getEnsembleProviderRef(), EnsembleProvider.class));
        } else {
            throw new IllegalArgumentException("[Assertion failed] 'connection-string' must be configured.");
        }

        if (StringUtils.hasLength(curatorProperties.getAclProviderRef())) {
            builder.aclProvider(beanFactory.getBean(curatorProperties.getAclProviderRef(), ACLProvider.class));
        }

        if (StringUtils.hasText(curatorProperties.getAuthInfosRef())) {
            List<AuthInfo> authInfos = beanFactory.getBean(curatorProperties.getAuthInfosRef(), List.class);
            builder.authorization(authInfos);
        } else if (StringUtils.hasText(curatorProperties.getScheme()) && StringUtils.hasText(curatorProperties.getAuthBase64Str())) {
            builder.authorization(curatorProperties.getScheme(), Base64Utils.decodeFromString(curatorProperties.getAuthBase64Str()));
        }

        if (curatorProperties.getCanBeReadOnly() != null) {
            builder.canBeReadOnly(curatorProperties.getCanBeReadOnly());
        }

        if (curatorProperties.getUseContainerParentsIfAvailable() != null && !curatorProperties.getUseContainerParentsIfAvailable()) {
            builder.dontUseContainerParents();
        }

        if (StringUtils.hasLength(curatorProperties.getCompressionProviderRef())) {
            builder.compressionProvider(beanFactory.getBean(curatorProperties.getCompressionProviderRef(), CompressionProvider.class));
        }

        if (curatorProperties.getDefaultDataBase64Str() != null) {
            builder.defaultData(Base64Utils.decodeFromString(curatorProperties.getDefaultDataBase64Str()));
        }

        if (StringUtils.hasText(curatorProperties.getNamespace())) {
            builder.namespace(curatorProperties.getNamespace());
        }

        // 重试策略
        if (null != retryPolicy) {
            builder.retryPolicy(retryPolicy);
        }

        if (null != curatorProperties.getSessionTimeOutMs()) {
            builder.sessionTimeoutMs(curatorProperties.getSessionTimeOutMs());
        }

        if (null != curatorProperties.getConnectionTimeoutMs()) {
            builder.connectionTimeoutMs(curatorProperties.getConnectionTimeoutMs());
        }

        if (null != curatorProperties.getMaxCloseWaitMs()) {
            builder.maxCloseWaitMs(curatorProperties.getMaxCloseWaitMs());
        }

        if (StringUtils.hasLength(curatorProperties.getThreadFactoryRef())) {
            builder.threadFactory(beanFactory.getBean(curatorProperties.getThreadFactoryRef(), ThreadFactory.class));
        }

        if (StringUtils.hasLength(curatorProperties.getZookeeperFactoryRef())) {
            builder.zookeeperFactory(beanFactory.getBean(curatorProperties.getZookeeperFactoryRef(), ZookeeperFactory.class));
        }

        log.info("Start curatorFramework -> {}, sessionTimeOutMs={}, connectionTimeoutMs={}",
                curatorProperties.getConnectString(),
                curatorProperties.getSessionTimeOutMs(),
                curatorProperties.getConnectionTimeoutMs());

        return builder.build();

//            CuratorFramework zk = CuratorFrameworkFactory.builder()
//                    .connectString("127.0.0.1:2181")
//                    .connectionTimeoutMs(curatorProperties.getConnectionTimeoutMs())
//                    .sessionTimeoutMs(curatorProperties.getSessionTimeOutMs())
//                    .retryPolicy(retryPolicy)
//                    .build();
//            return zk;
    }

    @Bean
    @ConditionalOnMissingBean
    public ZookeeperLock distributeLock(@Autowired CuratorFramework curatorFramework) {
        return new ZookeeperLock(curatorFramework);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
