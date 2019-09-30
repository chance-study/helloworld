package org.chance.distributelock.jdbc.configuration;

import org.chance.distributelock.api.Lock;
import org.chance.distributelock.jdbc.impl.SimpleJdbcLock;
import org.chance.distributelock.jdbc.service.JdbcLockSingleKeyService;
import org.chance.distributelock.jdbc.service.SimpleJdbcLockSingleKeyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class JdbcDistributedLockConfiguration {

    @Bean
    public Lock simpleJdbcLock(final JdbcLockSingleKeyService jdbcLockSingleKeyService) {
        return new SimpleJdbcLock(jdbcLockSingleKeyService);
    }

    @Bean
    public JdbcLockSingleKeyService jdbcLockSingleKeyService(final JdbcTemplate jdbcTemplate) {
        return new SimpleJdbcLockSingleKeyService(jdbcTemplate);
    }
}
