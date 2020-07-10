package org.chance.spring.feature.aop;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-10 14:39:18
 */
public class ControlFlowPointCutDemo {

    public static void main(String[] args) {

        ProxyFactory factory = new ProxyFactory(new Person());

        // 声明一个通知（此处使用环绕通知 MethodInterceptor ）
        Advice advice = (MethodInterceptor) invocation -> {
            System.out.println("============>放行前拦截...");
            Object obj = invocation.proceed();
            System.out.println("============>放行后拦截...");
            return obj;
        };
        ////声明一个aspectj切点,一张切面
        // 含义：Main类里面，方法名为funabc执行时，内部调用的任何代理的方法都会被拦截~~~ 它控制的是整个流程
        ControlFlowPointcut cut = new ControlFlowPointcut(ControlFlowPointCutDemo.class, "funabc");

        // 切点+通知
        Advisor advisor = new DefaultPointcutAdvisor(cut, advice);
        factory.addAdvisor(advisor);
        Person p = (Person) factory.getProxy();

        // 执行方法
        p.run();
        p.run(10);
        p.say();
        p.sayHi("Jack");
        p.say("Tom", 666);

        // 此处调用Main类，方法名为funabc的方法。内部代理对象的方法就都会被拦截上了
        funabc(p);

    }

    private static void funabc(Person person) {
        person.run();
        person.say();
    }

}
