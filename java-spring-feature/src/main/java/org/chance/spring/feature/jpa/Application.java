package org.chance.spring.feature.jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

/**
 * JPA自动配置类(无需EnableJpaRepositories、EntityScan)：<code>@JpaRepositoriesAutoConfiguration</code>
 * <p>
 * 扫描repository: <code>@EnableJpaRepositories(basePackages = "org.chance.spring.feature.jpa")</code>
 * 扫描entity: <code>@EntityScan(basePackages = "org.chance.spring.feature.jpa")</code>
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-08-12 17:11:01
 */
@SpringBootApplication(exclude = {FlywayAutoConfiguration.class, WebMvcAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
