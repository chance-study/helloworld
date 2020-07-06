package org.chance.spring.feature.aop;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.util.Arrays;

/**
 * 本来要想使用ASM和CGLIB，我们是需要引入cglib相关的jar包的。但是从Spring3.2以后，我们就不用再单独因此此Jar了，因为Spring已经帮我们集成在Spring-core里面了
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-06 10:46:10
 */
public class CglibAopProxyDemo {

    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory(new Demo());
        proxyFactory.addAdvice((MethodBeforeAdvice) (method, args1, target) -> {
                    System.out.println("你被拦截了：方法名为：" + method.getName() + " 参数为--" + Arrays.asList(args1));
                }
        );

        Demo demo = (Demo) proxyFactory.getProxy();
        //你被拦截了：方法名为：hello 参数为--[]
        //this demo show
        demo.hello();
    }

    // 不要再实现接口,就会用CGLIB去代理
    static class Demo {
        public void hello() {
            System.out.println("this demo show");
        }
    }

}
