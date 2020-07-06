package org.chance.spring.feature.aop;

import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.SpringObjenesis;
import org.springframework.objenesis.instantiator.ObjectInstantiator;

/**
 * 原文链接：https://blog.csdn.net/f641385712/java/article/details/88952482
 *
 * Objenesis：另一种实例化对象的方式:
 * 它专门用来创建对象，即使你没有空的构造函数，都木有问题~~ 可谓非常的强大
 * 它不使用构造方法创建Java对象，所以即使你有空的构造方法，也是不会执行的。
 *
 * Objenesis Vs class.newInstance
 * 从以上代码可以发现class构造器需要参数，而Objenesis可以绕过去， Objenesis主要应用场景：
 *
 * 序列化，远程调用和持久化 -对象需要实例化并存储为到一个特殊的状态，而没有调用代码
 * 代理，AOP库和Mock对象 -类可以被子类继承而子类不用担心父类的构造器。
 * 容器框架 -对象可以以非标准的方式被动态实例化（比如Spring就是容器框架）。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-06 10:46:02
 */
public class ObjenesisDemo {

    public static void main(String[] args) throws Exception {

        Objenesis objenesis = new ObjenesisStd();
        // 它竟然创建成功了
        MyDemo myDemo = objenesis.newInstance(MyDemo.class);
        System.out.println(myDemo); //com.fsx.maintest.MyDemo@1f32e575
        System.out.println(myDemo.code); //null  特别注意：这里是null，而不是10

        // 若直接这样创建 就报错 java.lang.InstantiationException: com.fsx.maintest.MyDemo
        System.out.println(MyDemo.class.newInstance());


        // 相当于生成了一个实例创建的工厂，接下来就可以很方便得创建实例了
        // 如果你要创建多个实例，建议这么来创建
        ObjectInstantiator<MyDemo> instantiator = objenesis.getInstantiatorOf(MyDemo.class);

        MyDemo myDemo1 = instantiator.newInstance();
        MyDemo myDemo2 = instantiator.newInstance();
        System.out.println(myDemo1);
        System.out.println(myDemo1.code); //null
        System.out.println(myDemo2);

        // 使用SpringObjenesis
        Objenesis springObjenesis = new SpringObjenesis();
        MyDemo springObjenesisDemo = springObjenesis.newInstance(MyDemo.class);

    }


    static class MyDemo {
        public String code = "10";

        public MyDemo(String code) {
            this.code = code;
        }
    }

}
