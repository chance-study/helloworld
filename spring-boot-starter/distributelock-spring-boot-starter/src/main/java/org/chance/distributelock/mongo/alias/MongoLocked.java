package org.chance.distributelock.mongo.alias;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import org.chance.distributelock.api.annotation.Interval;
import org.chance.distributelock.api.annotation.Locked;
import org.chance.distributelock.mongo.impl.SimpleMongoLock;
import org.springframework.core.annotation.AliasFor;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Locked(type = SimpleMongoLock.class)
public @interface MongoLocked {

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