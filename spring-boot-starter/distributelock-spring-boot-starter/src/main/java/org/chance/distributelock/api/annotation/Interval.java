package org.chance.distributelock.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Locked
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2019-09-23 10:52:46
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Interval {

    /**
     * Interval period.
     * By default, can be specified as 'property placeholder', e.g. {@code ${locked.interval}}.
     */
    String value();

    /**
     * Interval {@link TimeUnit} represented by {@link #value()}.
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

}
