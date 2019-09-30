package org.chance.distributelock.core.configuration;

import org.chance.distributelock.api.key.KeyGenerator;
import org.chance.distributelock.core.key.SpelKeyGenerator;
import org.chance.distributelock.core.advice.LockBeanPostProcessor;
import org.chance.distributelock.core.advice.LockTypeResolver;
import org.chance.distributelock.core.converter.BeanFactoryAwareIntervalConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * DistributedLockConfiguration
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
@Configuration
public class DistributedLockConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public LockBeanPostProcessor lockBeanPostProcessor(final ConfigurableBeanFactory configurableBeanFactory, final KeyGenerator keyGenerator,
                                                       @Autowired(required = false) final TaskScheduler taskScheduler) {
        return new LockBeanPostProcessor(new BeanFactoryAwareIntervalConverter(configurableBeanFactory), configurableBeanFactory::getBean, keyGenerator, taskScheduler);
    }

    @Bean
    @ConditionalOnMissingBean
    public KeyGenerator spelKeyGenerator(final ConversionService conversionService) {
        return new SpelKeyGenerator(conversionService);
    }

    @Bean
    @ConditionalOnMissingBean
    public LockTypeResolver lockTypeResolver(final ConfigurableBeanFactory configurableBeanFactory) {
        return configurableBeanFactory::getBean;
    }

    @Bean
    @ConditionalOnMissingBean
    public ConversionService conversionService() {
        return new DefaultConversionService();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "com.github.alturkovic.lock.task-scheduler.default", name = "enabled", havingValue = "true", matchIfMissing = true)
    public TaskScheduler taskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

}
