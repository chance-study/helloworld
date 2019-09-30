package org.chance.distributelock.zookeeper.configuration;


import lombok.Getter;
import lombok.Setter;
import org.apache.curator.RetryPolicy;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.AuthInfo;
import org.apache.curator.framework.api.ACLProvider;
import org.apache.curator.framework.api.CompressionProvider;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author vincentruan
 * @version 1.0.0
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.curator")
public class CuratorProperties {

    private String aclProviderRef;

    private String scheme;

    private String authBase64Str;

    private String authInfosRef;

    private Boolean canBeReadOnly;

    private Boolean useContainerParentsIfAvailable;

    private String compressionProviderRef;

    private String ensembleProviderRef;

    /**
     * list of servers to connect to
     */
    private String connectString = "127.0.0.1:2181";

    private String defaultDataBase64Str;

    private String namespace;

    /**
     * session timeout
     */
    private Integer sessionTimeOutMs = 300;

    /**
     * connection timeout
     */
    private Integer connectionTimeoutMs = 30;

    private Integer maxCloseWaitMs;

    private String threadFactoryRef;

    private String zookeeperFactoryRef;

    /**
     * initial amount of time to wait between retries
     */
    private int baseSleepTimeMs = 1 * 1000;

    /**
     * max number of times to retry
     */
    private int maxRetries = 5;
}
