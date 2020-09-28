package org.chance.spring.feature.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * 自动配置类: {@link org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration}
 * 参数配置类：{@link org.mybatis.spring.boot.autoconfigure.MybatisProperties}
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-08-12 17:11:01
 */
@SpringBootApplication(exclude = {FlywayAutoConfiguration.class, WebMvcAutoConfiguration.class}, scanBasePackages = {"org.chance.spring.feature.mybatis", "org.chance.spring.feature.jpa"})
@EnableJpaRepositories(basePackages = "org.chance.spring.feature.jpa")
@EntityScan(basePackages = "org.chance.spring.feature.jpa")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
