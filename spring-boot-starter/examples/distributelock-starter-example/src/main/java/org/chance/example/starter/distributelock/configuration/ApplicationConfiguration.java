package org.chance.example.starter.distributelock.configuration;

import org.chance.distributelock.jdbc.configuration.EnableJdbcDistributedLock;
import org.chance.example.starter.distributelock.service.HelloService;
import org.chance.example.starter.distributelock.service.LockedHelloService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableJdbcDistributedLock
public class ApplicationConfiguration {

    @Bean
    public HelloService helloService() {
        return new LockedHelloService();
    }
}