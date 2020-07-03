package org.chance.pattern.structural.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * DynamicSubject java 的动态代理 通过继承接口方式
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 17:27:28
 */
public class DynamicSubject implements InvocationHandler {

    private Object sub;

    public DynamicSubject() {
    }

    public DynamicSubject(Object obj) {
        sub = obj;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("DynamicSubject.invoke-before");
        method.invoke(sub, args);
        System.out.println("DynamicSubject.invoke-after");
        return null;
    }
}
