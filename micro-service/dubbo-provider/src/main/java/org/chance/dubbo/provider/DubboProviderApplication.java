package org.chance.dubbo.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * - classpath：只会到你的class路径中查找找文件。
 * - classpath*：不仅包含class路径，还包括jar文件中（class路径）进行查找。
 */
@SpringBootApplication
//@ImportResource({"classpath*:spring/*.xml"})
public class DubboProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboProviderApplication.class, args);
    }

}
