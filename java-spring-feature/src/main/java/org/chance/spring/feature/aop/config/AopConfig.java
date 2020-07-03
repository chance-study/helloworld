package org.chance.spring.feature.aop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

/**
 * EnableAspectJAutoProxy是源头
 *
 * {@link EnableAspectJAutoProxy}注解已经通过EnableAutoConfiguration自动配置了，无需在引入，spring boot 2.x默认使用cglib代理
 * 自动配置类：{@link org.springframework.boot.autoconfigure.aop.AopAutoConfiguration}
 *
 * 也可以手动引入 EnableAspectJAutoProxy
 * - 则需要在启动类上面排序掉@SpringBootApplication(exclude = {AopAutoConfiguration.class})
 * - proxyTargetClass 决定该类采用CGLIB代理还是使用JDK的动态代理（需要实现接口），默认为false，表示使用的是JDK得动态代理技术
 * - exposeProxy 代理的暴露方式：解决内部调用不能使用代理的场景  默认为false表示不处理
 *     true：这个代理就可以通过AopContext.currentProxy()获得这个代理对象的一个副本（ThreadLocal里面）,从而我们可以很方便得在Spring框架上下文中拿到当前代理对象（处理事务时很方便）
 // 必须为true才能调用AopContext得方法，否则报错：Cannot find current proxy: Set 'exposeProxy' property on Advised to 'true' to make it available.
 * 异步方法建议尽量处理耗时的任务，或者是处理不希望阻断主流程的任务（比如异步记录操作日志）
 * 对应的@Enable注解，最好写在属于自己的配置文件上，保持内聚性
 *
 * @see org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator 自动代理创建器（AOP自动代理的核心）:
 *
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-28 16:17:44
 */
@EnableAspectJAutoProxy(proxyTargetClass = false, exposeProxy = true)
@Configuration
@Profile("aop")
public class AopConfig {

}
