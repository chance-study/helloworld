package org.chance.spring.feature.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;

/**
 *
 */
@SpringBootApplication(scanBasePackageClasses = {RootConfig.class, AppConfig.class}, exclude = FlywayAutoConfiguration.class)
public class Client {

    public static void main(String[] args) {
        SpringApplication.run(Client.class, args);
    }

}
