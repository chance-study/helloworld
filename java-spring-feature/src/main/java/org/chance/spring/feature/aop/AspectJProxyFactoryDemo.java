package org.chance.spring.feature.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.chance.spring.feature.service.HelloService;
import org.chance.spring.feature.service.impl.HelloServiceImpl;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-06 14:41:46
 */
public class AspectJProxyFactoryDemo {

    public static void main(String[] args) {
        AspectJProxyFactory proxyFactory = new AspectJProxyFactory(new HelloServiceImpl());
        // 注意：此处得MyAspect类上面的@Aspect注解必不可少
        // 这里就能自动帮我们完成了方法、通知的绑定工作
        proxyFactory.addAspect(MyAspect.class);
        //proxyFactory.setProxyTargetClass(true);//是否需要使用CGLIB代理
        HelloService proxy = proxyFactory.getProxy();
        proxy.hello();

        System.out.println(proxy.getClass()); //class com.sun.proxy.$Proxy6
    }

    @Aspect
    static class MyAspect {

        @Pointcut("execution(* hello(..))")
        private void beforeAdd() {
        }

        @Before("beforeAdd()")
        public void before1() {
            System.out.println("-----------before-----------");
        }

    }

}
