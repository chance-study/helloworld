package org.chance.lang;

/**
 * JAVA类装载方式，有两种：
 * 隐式装载， 程序在运行过程中当碰到通过 new 等方式生成对象时，隐式调用类装载器加载对应的类到jvm中。
 * 显式装载， 通过class.forname()等方法，显式加载需要的类
 * <p>
 * 有三种默认使用的类加载器：Bootstrap类加载器、Extension类加载器和System类加载器（或者叫作Application类加载器）。每种类加载器都有设定好从哪里加载类。
 * Bootstrp加载器：是用C++语言写的（其余均为Java写的），它是在Java虚拟机启动后初始化的，它主要负责加载rt.jar中的类。（JDK的核心类，如String、Integer等等类） 它对rt.jar的加载全盘负责
 * ExtClassLoader：Bootstrp loader加载ExtClassLoader,并且将ExtClassLoader的父加载器设置为Bootstrp loader.ExtClassLoader。是用Java写的，具体来说就是 sun.misc.Launcher$ExtClassLoader。xtClassLoader主要加载%JAVA_HOME%/jre/lib/ext，此路径下的所有classes目录以及java.ext.dirs系统变量指定的路径中类库。
 * AppClassLoader：Bootstrp loader加载完ExtClassLoader后，就会加载AppClassLoader,并且将AppClassLoader的父加载器指定为 ExtClassLoader。AppClassLoader也是用Java写成的，它的实现类是 sun.misc.Launcher$AppClassLoader。 另外我们知道ClassLoader中有个getSystemClassLoader方法,此方法返回的正是AppclassLoader。
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-16 10:06:08
 */
public class ClassLoaderDemo {

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.ext.dirs"));
        System.out.println(System.getProperty("java.class.path"));


        /**
         * Bootstrap Loader是用C++语言写的，依java的观点来看，逻辑上并不存在Bootstrap Loader的类实体，所以在java程序代码里试图打印出其内容时，我们就会看到输出为null。
         */
        System.out.println(String.class.getClassLoader()); //null

        System.out.println(ClassLoaderDemo.class.getClassLoader().getParent()); //sun.misc.Launcher$ExtClassLoader@23fc625e

        // Class类怎么装在的？
        // Class类没有public的构造方法，Class对象是在装载类时由JVM通过调用类装载器中的defineClass()方法自动构造的。

    }

}
