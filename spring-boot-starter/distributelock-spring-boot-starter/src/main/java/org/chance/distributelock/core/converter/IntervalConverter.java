package org.chance.distributelock.core.converter;

import org.chance.distributelock.api.annotation.Interval;

/**
 * IntervalConverter
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
@FunctionalInterface
public interface IntervalConverter {

    /**
     * Convert {@link Interval} to milliseconds.
     *
     * @param interval interval to convert
     * @return milliseconds represented by the given {@code interval}
     */
    long toMillis(Interval interval);

}
