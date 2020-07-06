package org.chance.spring.feature.aop;

import org.springframework.cglib.core.DefaultGeneratorStrategy;
import org.springframework.cglib.core.SpringNamingPolicy;
import org.springframework.cglib.proxy.CallbackHelper;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.SpringObjenesis;

import java.lang.reflect.Method;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-06 11:13:00
 */
public class EnhancerObjenesisDemo {

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(MyDemo.class);

        // 如国实用createClass方式来创建代理的实例  是不能直接添加callback得
        //enhancer.setCallback();
        enhancer.setNamingPolicy(SpringNamingPolicy.INSTANCE);
        enhancer.setStrategy(new DefaultGeneratorStrategy());
        enhancer.setCallbackFilter(new CallbackHelper(MyDemo.class, null) {
            @Override
            protected Object getCallback(Method method) {
                return (MethodInterceptor) (o, method1, args1, methodProxy) -> {
                    System.out.println(method1.getName() + "---方法拦截前");
                    // 此处千万不能调用method得invoke方法，否则会死循环的 只能使用methodProxy.invokeSuper 进行调用
                    //Object result = method.invoke(o, args1);
                    Object result = methodProxy.invokeSuper(o, args1);
                    System.out.println(method1.getName() + "---方法拦截后");
                    return result;
                };
            }
        });
        enhancer.setCallbackTypes(new Class[]{MethodInterceptor.class});

        // 这里我们只生成Class字节码，并不去创建对象
        Class clazz = enhancer.createClass();
        // 创建对象的操作交给
        Objenesis objenesis = new SpringObjenesis();
        MyDemo myDemo = (MyDemo) objenesis.newInstance(clazz);

        System.out.println(myDemo);
        System.out.println(myDemo.code);

    }

    static class MyDemo {
        public String code = "10";

        public MyDemo(String code) {
            this.code = code;
        }
    }

}
