package org.chance.elasticjob.annotation;

import org.chance.elasticjob.autoconfigure.JobParserAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * EnableElasticJob
 * <p>
 * ElasticJob 开启注解
 *
 * <p>在Spring Boot 启动类上加此注解开启自动配置<p>
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2019/8/28
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({JobParserAutoConfiguration.class})
public @interface EnableElasticJob {
}
