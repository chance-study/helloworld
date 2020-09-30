package org.chance.spring.feature.zookeeper;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;

/**
 * 自动配置类: {@link MybatisAutoConfiguration}
 * 参数配置类：{@link org.mybatis.spring.boot.autoconfigure.MybatisProperties}
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-08-12 17:11:01
 */
@SpringBootApplication(exclude = {FlywayAutoConfiguration.class, WebMvcAutoConfiguration.class, MybatisAutoConfiguration.class, JpaRepositoriesAutoConfiguration.class, CacheAutoConfiguration.class, RedisAutoConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
