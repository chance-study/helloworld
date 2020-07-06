package org.chance.spring.feature.aop;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Advisor;
import org.springframework.aop.DynamicIntroductionAdvice;
import org.springframework.aop.IntroductionInterceptor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultIntroductionAdvisor;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-06 17:14:40
 */
public class SomeInteIntroductionInterceptorDemo implements IntroductionInterceptor, IOtherInte {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (implementsInterface(invocation.getMethod().getDeclaringClass())) {
            System.out.println("我是引介增强的方法体~~~invoke");
            return invocation.getMethod().invoke(this, invocation.getArguments());
        }
        return invocation.proceed();
    }

    @Override
    public boolean implementsInterface(Class<?> intf) {
        return intf.isAssignableFrom(IOtherInte.class);
    }

    @Override
    public void doOther() {
        System.out.println("给人贴标签 doOther...");
    }

    // 方法测试
    public static void main(String[] args) {
        ProxyFactory factory = new ProxyFactory(new ProxyFactoryBeanDemo.Person());
        factory.setProxyTargetClass(true); // 强制私用CGLIB 以保证我们的Person方法也能正常调用

        // 此处采用IntroductionInterceptor 这个引介增强的拦截器
        Advice advice = new SomeInteIntroductionInterceptorDemo();

        // 切点+通知（注意：此处放的是复合切面）
        Advisor advisor = new DefaultIntroductionAdvisor((DynamicIntroductionAdvice) advice, IOtherInte.class);
        //Advisor advisor = new DefaultPointcutAdvisor(cut, advice);
        factory.addAdvisor(advisor);

        IOtherInte otherInte = (IOtherInte) factory.getProxy();
        otherInte.doOther();

        System.out.println("===============================");

        // Person本身自己的方法  也得到了保留
        ProxyFactoryBeanDemo.Person p = (ProxyFactoryBeanDemo.Person) factory.getProxy();
        p.run();
        p.say();
    }

}

interface IOtherInte {
    void doOther();
}
