package org.chance.spring.feature.aop;

import org.chance.spring.feature.service.HelloService;
import org.chance.spring.feature.service.impl.HelloServiceImpl;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.framework.ProxyFactory;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-06 14:10:30
 */
public class ProxyFactoryDemo {
    public static void main(String[] args) {

        ProxyFactory proxyFactory = new ProxyFactory(new HelloServiceImpl());

        // 添加两个Advise，一个匿名内部类表示
        proxyFactory.addAdvice((AfterReturningAdvice) (returnValue, method, args1, target) ->
                System.out.println("AfterReturningAdvice method=" + method.getName()));
//        proxyFactory.addAdvice(new LogMethodBeforeAdvice());

        HelloService proxy = (HelloService) proxyFactory.getProxy();
        proxy.hello();

    }
}
