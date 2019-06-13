package org.chance.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * DynamicSubject
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/6/14
 */ //java 的动态代理 通过继承接口方式
class DynamicSubject implements InvocationHandler {
    private Object sub;
    public DynamicSubject(){}
    public DynamicSubject(Object obj){
        sub=obj;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("DynamicSubject.invoke-before");
        method.invoke(sub, args);
        System.out.println("DynamicSubject.invoke-after");
        return null;
    }
}
