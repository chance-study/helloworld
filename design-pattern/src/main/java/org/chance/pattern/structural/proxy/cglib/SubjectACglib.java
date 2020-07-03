package org.chance.pattern.structural.proxy.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * SubjectACglib
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 17:28:37
 */
public class SubjectACglib implements MethodInterceptor {

    private Object target;

    //创建代理对象
    public Object getInstance(Object target) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
//        设置回调方法
        enhancer.setCallback(this);
        //创建代理对象
        return enhancer.create();
    }

    @Override
    //回调方法
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        System.out.println("SubjectACglib.intercept>>>before");
        proxy.invokeSuper(obj, args);
        System.out.println("SubjectACglib.intercept>>>after");
        return null;
    }
}
