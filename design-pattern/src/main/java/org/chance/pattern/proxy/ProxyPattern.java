package org.chance.pattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by gengchao on 16/4/28.
 * 代理模式
 * Provide a surrogate or placeholder for another object to controlaccess to it
 * (为其他对象提供一种代理以控制对这个对象的访问)
 * Subject：抽象主题角色，抽象主题类可以是抽象类，也可以是接口，是一个最普通的业务类型定义，
 * 无特殊要求。
 * RealSubject：具体主题角色，也叫被委托角色、被代理角色。是业务逻辑的具体执行者。
 * Proxy：代理主题角色，也叫委托类、代理类。它把所有抽象主题类定义的方法给具体主题角色实现，
 * 并且在具体主题角色处理完毕前后做预处理和善后工作。（最简单的比如打印日志）
 */
public class ProxyPattern {
    public static void main(String[] args) {

        Subject subject = new RealSubject();
        Proxy proxy = new Proxy(subject);
        proxy.op();

        /****/
        //动态代理
        InvocationHandler ds = new DynamicSubject(subject);
        Class cls = subject.getClass();
        Subject subject1 = (Subject) java.lang.reflect.Proxy.newProxyInstance(cls.getClassLoader()
        ,cls.getInterfaces(),ds);
        subject1.op();

        /**cglib 进行代理*/
        SubjectACglib cglib = new SubjectACglib();
        SubjectA subjectA= (SubjectA) cglib.getInstance(new SubjectA());
        subjectA.op();
        System.out.println("cglib>>>>"+subjectA.toString());

        /****/
        Collection proxy3 = (Collection)
                java.lang.reflect.Proxy.newProxyInstance(
                        Collection.class.getClassLoader(),
                        new Class[]{Collection.class},
                        new InvocationHandler(){
                            ArrayList target = new ArrayList();
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                long start = System.currentTimeMillis();
                                Object retVal = method.invoke(target, args);
                                long end = System.currentTimeMillis();
                                System.out.println("ProxyPattern.invoke>>>Method:"+method.getName()+">>>runtime is"+(end-start));
                                return retVal;
                            }
                        }
                );
        proxy3.add("AAAA");
        System.out.println(proxy3.toString());
        System.out.println("ProxyPattern.main>>>>"+proxy3.getClass().getName());


    }



}


