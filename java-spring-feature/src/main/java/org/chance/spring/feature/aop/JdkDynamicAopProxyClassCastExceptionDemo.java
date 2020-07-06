package org.chance.spring.feature.aop;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.util.Arrays;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-03 16:52:39
 */
public class JdkDynamicAopProxyClassCastExceptionDemo {

    public static void main(String[] args) {

        ProxyFactory proxyFactory = new ProxyFactory(new Demo());
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args1, target) -> {
                    System.out.println("你被拦截了：方法名为：" + method.getName() + " 参数为--" + Arrays.asList(args1));
                }
        );

        // 如果使用的是JDK的动态代理，这里若实现实现类接收，就报错：java.lang.ClassCastException: com.fsx.maintest.$Proxy0 cannot be cast to com.fsx.maintest.Demo
        //Demo demo = (Demo) proxyFactory.getProxy();
        DemoInter demo = (DemoInter) proxyFactory.getProxy();
        //你被拦截了：方法名为：hello 参数为--[]
        //this demo show
        demo.hello();

    }

    // 不要再实现接口,就会用CGLIB去代理
    static class Demo implements DemoInter {

        @Override
        public void hello() {
            System.out.println("this demo show");
        }
    }

    static interface DemoInter {
        void hello();
    }

}

