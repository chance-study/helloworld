package org.chance.spring.feature.aop;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.ControlFlowPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-10 14:45:57
 */
public class ComposablePointcutDemo {

    public static void main(String[] args) {

        ProxyFactory factory = new ProxyFactory(new Person());

        // 声明一个通知（此处使用环绕通知 MethodInterceptor ）
        Advice advice = (MethodInterceptor) invocation -> {
            System.out.println("============>放行前拦截...");
            Object obj = invocation.proceed();
            System.out.println("============>放行后拦截...");
            return obj;
        };

        // 先创建一个流程切入点
        ControlFlowPointcut controlFlowPointcut = new ControlFlowPointcut(ComposablePointcutDemo.class, "funabc");
        // 再创建一个方法名切入点
        NameMatchMethodPointcut nameMatchMethodPointcut = new NameMatchMethodPointcut();
        nameMatchMethodPointcut.addMethodName("say");

        // 创建一个复合切点 把上面两者并且进来
        ComposablePointcut cut = new ComposablePointcut();
        cut.intersection((Pointcut) controlFlowPointcut).intersection((Pointcut) nameMatchMethodPointcut);

        // 切点+通知（注意：此处放的是复合切面）
        Advisor advisor = new DefaultPointcutAdvisor(cut, advice);
        factory.addAdvisor(advisor);
        Person p = (Person) factory.getProxy();

        // 执行方法
        p.run();
        p.run(10);
        p.say();
        p.sayHi("Jack");
        p.say("Tom", 666);

        funabc(p);
    }

    private static void funabc(Person person) {
        person.run();
        person.say();

    }


}
