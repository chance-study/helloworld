package org.chance.javassist;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * $ 开头的几个标识符有特殊的含义：
 * $0, $1, $2, ... this and 方法的参数
 * $args 方法参数数组.它的类型为 Object[]
 * $$ 所有实参。例如, m($$) 等价于 m($1,$2,...)
 * $cflow(...) cflow 变量
 * $r 返回结果的类型，用于强制类型转换
 * $w 包装器类型，用于强制类型转换
 * $_ 返回值
 * $sig 类型为 java.lang.Class 的参数类型数组
 * $type 一个 java.lang.Class 对象，表示返回值类型
 * $class 一个 java.lang.Class 对象，表示当前正在修改的类
 *
 * JavassistDemo
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/25
 */
public class JavassistDemo {

    /**
     * 动态生成一个全新的类
     */
    public static void newClass() throws CannotCompileException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        // ClassPool包含所有动态生成的类，getDefault()返回默认的ClassPool，
        ClassPool cp = ClassPool.getDefault();

        // 动态生成一个类
        CtClass gclazz = cp.makeClass("org.chance.javassist.GeneratedClass");
        CtMethod gmethod = CtMethod
                .make("public void sayHello() { System.out.println(\"Hello Javaassist\"); }", gclazz);
        gclazz.addMethod(gmethod);

        // 转换成Class
        Class<?> gc = gclazz.toClass();
        // 将该CtClass从ClassPool中移除，
        gclazz.detach();

        // 调用方法
        Object ginst = gc.newInstance();
        Method gm = gc.getMethod("sayHello");

        gm.invoke(ginst);

    }


    /**
     * 动态修改类
     */
    public static void modifyClass() throws CannotCompileException, NotFoundException {

        // ClassPool包含所有动态生成的类，getDefault()返回默认的ClassPool，
        ClassPool cp = ClassPool.getDefault();

        // 该ClassPool默认从java lib，ext，classpath搜索class文件，并生成一个CtClass返回
        CtClass clazz = cp.get("org.chance.javassist.TestClass");

        // 修改compute方法
        CtMethod method = clazz.getDeclaredMethod("compute");

        // $args，参数（$1，第一个参数，$2，$3以此类推) ，$_：返回值
        method.insertAfter("System.out.println(\"compute called with param: \" + java.util.Arrays.toString($args) + \", return: \" + $_);");

        // 转换成Class，这一步也载入了修改后的Class。注意：必须保证之前这个Class没有载入过，不然会报异常：java.lang.LinkageError，因为JVM不允许一个class多次加载
        clazz.toClass();
        // 将该CtClass从ClassPool中移除，
        clazz.detach();

        // 这时载入的TestClass已经被修改
        TestClass test = new TestClass();
        // 调用方法
        test.compute(5);
    }


    /**
     * 拦截方法
     */
    public static void interceptClass() throws IllegalAccessException, InstantiationException {

        ProxyFactory factory = new ProxyFactory();
        // 设置父类，ProxyFactory将会动态生成一个类，继承该父类
        factory.setSuperclass(TestClass.class);
        // 设置过滤器，判断哪些方法调用需要被拦截
        factory.setFilter(new MethodFilter() {
            @Override
            public boolean isHandled(Method m) {
                if (m.getName().equals("compute")) {
                    return true;
                }
                return false;
            }
        });

        Class<?> c = factory.createClass();
        TestClass object = (TestClass) c.newInstance();
        // 设置拦截处理
        ((Proxy) object).setHandler(new MethodHandler() {
            @Override
            public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
                long start = System.currentTimeMillis();
                try {
                    return proceed.invoke(self, args);
                } catch (Exception e) {
                    throw e;
                } finally {
                    long taken = System.currentTimeMillis() - start;
                    System.out.println("call method: " + thisMethod.getName() + " take: " + taken + "ms");
                }
            }
        });

        // 调用方法
        System.out.println(object.compute(11));
    }

}
