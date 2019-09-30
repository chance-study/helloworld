package org.chance.distributelock.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EnableDistributedLock
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
@Configuration
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(DistributedLockConfiguration.class)
public @interface EnableDistributedLock {

}
