package org.chance.distributelock.core.converter;

import org.chance.distributelock.api.annotation.Interval;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.util.StringUtils;

/**
 * BeanFactoryAwareIntervalConverter
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
public class BeanFactoryAwareIntervalConverter implements IntervalConverter {

    private final ConfigurableBeanFactory beanFactory;

    public BeanFactoryAwareIntervalConverter(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public long toMillis(Interval interval) {
        final String value = beanFactory.resolveEmbeddedValue(interval.value());
        if (!StringUtils.hasText(value)) {
            throw new IllegalArgumentException("Cannot convert interval " + interval + " to milliseconds");
        }
        return interval.unit().toMillis(Long.valueOf(value));
    }

}
