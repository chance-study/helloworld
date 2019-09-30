package org.chance.distributelock.redis.configuration;

import org.chance.distributelock.api.Lock;
import org.chance.distributelock.redis.impl.MultiRedisLock;
import org.chance.distributelock.redis.impl.SimpleRedisLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * RedisDistributedLockConfiguration
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/24
 */
@Configuration
public class RedisDistributedLockConfiguration {

    @Bean
    public Lock simpleRedisLock(final StringRedisTemplate stringRedisTemplate) {
        return new SimpleRedisLock(stringRedisTemplate);
    }

    @Bean
    public Lock multiRedisLock(final StringRedisTemplate stringRedisTemplate) {
        return new MultiRedisLock(stringRedisTemplate);
    }
}
