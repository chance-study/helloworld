package org.chance.strategy.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * StrategyAutoConfiguration
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/7/30
 */

@Configuration
//@AutoConfigureBefore(JmsAutoConfiguration.class)
@ConditionalOnClass({ApplicationContext.class})
//@ConditionalOnMissingBean(ConnectionFactory.class)
@EnableConfigurationProperties(StrategyProperties.class)
@Import(ApplicationContextHolder.class)
public class StrategyAutoConfiguration {

    private static final Logger log = LoggerFactory
            .getLogger(StrategyAutoConfiguration.class);

    /**
     * 只有一个有参构造器的情况下，参数的值就会从容器中拿
     */
    public StrategyAutoConfiguration(StrategyProperties properties) {
        if (properties != null && !properties.isLazy()) {
            log.info("StrategyFactory loading");
            StrategyFactory.getInstance();
        }
    }

}
