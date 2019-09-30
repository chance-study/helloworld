package org.chance.distributelock.mongo.configuration;

import org.chance.distributelock.api.Lock;
import org.chance.distributelock.mongo.impl.SimpleMongoLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDistributedLockConfiguration {

    @Bean
    public Lock simpleMongoLock(final MongoTemplate mongoTemplate) {
        return new SimpleMongoLock(mongoTemplate);
    }
}
