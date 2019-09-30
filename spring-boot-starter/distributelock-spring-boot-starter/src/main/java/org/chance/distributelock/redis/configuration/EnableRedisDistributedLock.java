package org.chance.distributelock.redis.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.chance.distributelock.core.configuration.DistributedLockConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * EnableZookeeperDistributedLock
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/24
 */
@Configuration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import({DistributedLockConfiguration.class, RedisDistributedLockConfiguration.class})
public @interface EnableRedisDistributedLock {
}