package org.chance.distributelock.zookeeper.alias;

import org.chance.distributelock.api.annotation.Interval;
import org.chance.distributelock.api.annotation.Locked;
import org.chance.distributelock.zookeeper.impl.ZookeeperLock;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * ZookeeperLocked
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Locked(type = ZookeeperLock.class)
public @interface ZookeeperLocked {

    @AliasFor(annotation = Locked.class)
    boolean manuallyReleased() default false;

    @AliasFor(annotation = Locked.class)
    String storeId() default "lock";

    @AliasFor(annotation = Locked.class)
    String prefix() default "";

    @AliasFor(annotation = Locked.class)
    String expression() default "#executionPath";

    @AliasFor(annotation = Locked.class)
    Interval expiration() default @Interval(value = "10", unit = TimeUnit.SECONDS);

    @AliasFor(annotation = Locked.class)
    Interval timeout() default @Interval(value = "1", unit = TimeUnit.SECONDS);

    @AliasFor(annotation = Locked.class)
    Interval retry() default @Interval(value = "50");

    @AliasFor(annotation = Locked.class)
    Interval refresh() default @Interval(value = "0");
}
