package org.chance.spring.feature.aop;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-10 10:31:12
 */
public class JdkRegexpMethodPointcutDemo {

    public static void main(String[] args) {

        ProxyFactory factory = new ProxyFactory(new ProxyFactoryBeanDemo.Person());

        //声明一个aspectj切点,一张切面
        JdkRegexpMethodPointcut cut = new JdkRegexpMethodPointcut();

        //cut.setPattern("com.fsx.maintest.Person.run"); //它会拦截Person类下所有run的方法（无法精确到方法签名）
        //cut.setPattern(".*run.*");//.号匹配除"\r\n"之外的任何单个字符。*号代表零次或多次匹配前面的字符或子表达式  所以它拦截任意包下任意类的run方法
        cut.setPatterns(new String[]{".*run.*", ".*say.*"}); //可以配置多个正则表达  式...  sayHi方法也会被拦截

        // 声明一个通知（此处使用环绕通知 MethodInterceptor ）
        Advice advice = (MethodInterceptor) invocation -> {
            System.out.println("============>放行前拦截...");
            Object obj = invocation.proceed();
            System.out.println("============>放行后拦截...");
            return obj;
        };

        //切面=切点+通知
        // 它还有个构造函数：DefaultPointcutAdvisor(Advice advice); 用的切面就是Pointcut.TRUE，所以如果你要指定切面，请使用自己指定的构造函数
        // Pointcut.TRUE：表示啥都返回true，也就是说这个切面作用于所有的方法上/所有的方法
        // addAdvice();方法最终内部都是被包装成一个 `DefaultPointcutAdvisor`，且使用的是Pointcut.TRUE切面，因此需要注意这些区别  相当于new DefaultPointcutAdvisor(Pointcut.TRUE,advice);
        Advisor advisor = new DefaultPointcutAdvisor(cut, advice);

        factory.addAdvisor(advisor);

        ProxyFactoryBeanDemo.Person p = (ProxyFactoryBeanDemo.Person) factory.getProxy();

        // 执行方法
        p.run();
        p.run(10);
        p.say();
        p.sayHi("Jack");
        p.say("Tom", 666);

    }

}
