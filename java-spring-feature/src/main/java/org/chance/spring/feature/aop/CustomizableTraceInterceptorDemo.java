package org.chance.spring.feature.aop;

import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.interceptor.CustomizableTraceInterceptor;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-09 16:21:05
 */
public class CustomizableTraceInterceptorDemo {

    public static void main(String[] args) {

        ProxyFactory factory = new ProxyFactory(new ProxyFactoryBeanDemo.Person());
        factory.setProxyTargetClass(true); // 强制私用CGLIB 以保证我们的Person方法也能正常调用

        // 记录日志的切面
        CustomizableTraceInterceptor advice = new CustomizableTraceInterceptor();
        PerformanceMonitorInterceptor performanceMonitorInterceptor = new PerformanceMonitorInterceptor();

        // 切点+通知（注意：此处放的是复合切面）
        Advisor advisor = new DefaultPointcutAdvisor(advice);
        Advisor advisor1 = new DefaultPointcutAdvisor(performanceMonitorInterceptor);

        factory.addAdvisor(advisor);
        factory.addAdvisor(advisor1);
        ProxyFactoryBeanDemo.Person p = (ProxyFactoryBeanDemo.Person) factory.getProxy();

        p.run();
        p.say();

    }

}
