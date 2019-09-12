package org.chance.dubbo.consumer.config;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * DubboConsumerConfiguration
 * <p>
 * dubbo-consumer.properties的配置文件推荐使用dubbo-consumer.xml
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2019-09-09 18:36:24
 */
@Configuration
@EnableDubbo(scanBasePackages = "org.chance.dubbo.consumer")
//@PropertySource("classpath:spring/dubbo-consumer.properties")
@ImportResource({"classpath:spring/dubbo-consumer.xml"})
public class DubboConsumerConfiguration {
}
