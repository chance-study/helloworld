package org.chance.spring.feature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;

/**
 * -Dserver.port=8080 -Dh2.tcp.port=9092 spring.profiles.active=dev
 * -Dserver.port=8081 spring.profiles.active=dev2
 * <p>
 * dev2 依赖dev的数据库
 */

@SpringBootApplication(exclude = {AopAutoConfiguration.class})
public class JavaSpringFeatureApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaSpringFeatureApplication.class, args);
    }

}
