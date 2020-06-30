package org.chance.spring.feature.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 *
 *
 *StaticMethodMatcherPointcut
 * try{
 *     try{
 *         //@Before
 *         method.invoke(..);
 *     }finally{
 *         //@After
 *     }
 *     //@AfterReturning
 * }catch(){
 *     //@AfterThrowing
 * }
 *
 * 前置通知 @Before 相当于 BeforeAdvice
 * 后置通知 @AfterReturning 相当于 AfterReturningAdvice
 * 环绕通知 @Around 相当于 MethodInterceptor
 * 抛出通知 @AfterThrowing 相当于 ThrowAdvice
 * 最终通知 @After 不管是否异常，该通知都会执行
 * 引介增强：org.springframework.aop.IntroductionInterceptor，表示在目标类中添加一些新的方法和属性。
 *
 * # 切点类型：Spring提供了六种类型切点:
 * 静态方法切点：org.springframework.aop.support.StaticMethodMatcherPointcut是静态方法切点的抽象基类，默认情况下它匹配所有的类。（NameMatchMethodPointcut提供简单字符串匹配方法签名，AbstractRegexpMethodPointcut使用正则表达式匹配方法签名。） 它不考虑方法入参个数、类型匹配
 * 动态方法切点：org.springframework.aop.support.DynamicMethodMatcherPointcut是动态方法的抽象基类，默认情况下它匹配所有的类 它会考虑方法入参个数、类型匹配
 * 注解切点：org.springframework.aop.support.annotation.AnnotationMatchingPointcut实现类表示注解切点。使用AnnotationMatchingPointcut支持在Bean中直接通过JDK 5.0注解标签定义切点
 * 表达式切点：org.springframework.aop.support.ExpressionPointcut接口主要是为了支持AspectJ切点表达式语法而定义的接口。 这个是最强大的，Spring支持11种切点表达式
 * 流程切点：org.springframework.aop.support.ControlFlowPointcut实现类表示控制流程切点。ControlFlowPointcut是一种特殊的切点，它根据程序执行堆栈的信息查看目标方法是否由某一个方法直接或间接调用，以此判断是否为匹配的连接点。
 * 复合切点：org.springframework.aop.support.ComposablePointcut实现类是为创建多个切点而提供的方便操作类。它所有的方法都返回ComposablePointcut类。

 * # 切面类型
 * 切面的分类：
 *
 * Advisor：代表一般切面，它仅包含一个Advice。这个切面太宽泛，一般不会直接使用。
 * PointcutAdvisor：代表具有切点的切面，它包含Advice和Pointcut两个类。
 * IntroductionAdvisor：代表引介切面。引介切面是对应引介增强的特殊的切面，它应用于类层面上。
 * PointcutAdvisor主要有6个具体的实现类：
 *
 * DefaultPointcutAdvisor：最常用的切面类型
 * NameMatchMethodPointcutAdvisor:通过该类可以定义按方法名定义切点的切面
 * RegexpMethodPointcutAdvisor:对于按正则表达式匹配方法名进行切点定义的切面，可以通过扩展该实现类进行操作。
 * StaticMethodMatcherPointcutAdvisor:静态方法匹配器切点定义的切面，默认情况下，匹配所有的目标类。
 * AspectJExpressionPointcutAdvisor：用于AspectJ切点表达式定义切点的切面
 * AspectJPointcutAdvisor:用于AspectJ语法定义的切面。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-29 15:59:13
 */
@Slf4j
@Aspect
@Component
public class LogAspect {

    // 只拦截service层的所有方法
    @Pointcut("execution(* org.chance.spring.feature.service.impl.*.*(..))")
    public void point() {

    }

    @Before("point()")
    public void before() {
        log.info("this is from LogAspect#before...");
    }

    @After("point()")
    public void after() {
        log.info("this is from LogAspect#after...");
    }

    @AfterReturning("point()")
    public void afterReturning() {
        log.info("this is from LogAspect#afterReturning...");
    }

    @AfterThrowing("point()")
    public void afterThrowing() {
        log.info("this is from LogAspect#afterThrowing...");
    }

    // 此处需要注意：若写了@Around方法，那么最后只会执行@Around和@AfterReturning 其它的都不会执行
    //@Around("point()")
    //public void around() {
    //    log.info("this is from LogAspect#around...");
    //}
}
