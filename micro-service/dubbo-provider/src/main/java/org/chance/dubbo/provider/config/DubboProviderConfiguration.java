package org.chance.dubbo.provider.config;

import org.apache.dubbo.config.ProviderConfig;
import org.apache.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.chance.dubbo.provider.service.ValidationServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/**
 * DubboProviderConfiguration
 *
 * Q&A
 * - springboot 集成dubbo项目，@EnableDubbo需放到@SpringBootApplication注解类下面才生效，放到子包中得其他@Configuration中不生效
 * 应该是注解AliasFor没生效吧，spring版本过低了。
 * 不升级spring的话直接用DubboComponentScan吧
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/9
 */

@Configuration
@EnableDubbo(scanBasePackages = "org.chance.dubbo.provider.service")
@DubboComponentScan("org.chance.dubbo.provider.service")
//@PropertySource("classpath:spring/dubbo-provider.properties")
@ImportResource({"classpath:spring/dubbo-provider.xml"})
public class DubboProviderConfiguration {

    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(1000);
        return providerConfig;
    }

}
