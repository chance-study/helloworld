package org.chance.java.unsafe;

import org.chance.wrapper.UnsafeWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * UnsafeDefineClassDemo
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/14
 */
public class UnsafeDefineClassDemo {

    public static void main(String[] args) throws IOException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        /*
         *
         * //告诉JVM定义一个类，返回类实例，此方法会跳过JVM的所有安全检查。
         * public native Class defineClass(String name, byte[] b, int off, int len, ClassLoader loader, ProtectionDomain protectionDomain);
         * //加载一个匿名类
         * public native Class defineAnonymousClass(Class hostClass, byte[] data, Object[] cpPatches);
         * //判断是否需要加载一个类
         * public native boolean shouldBeInitialized(Class<?> c);
         * //确保类一定被加载
         * public native  void ensureClassInitialized(Class<?> c)
         */

        File f = new File(Thread.currentThread().getContextClassLoader().getResource("").getPath() + "/org/chance/object/TestDate.class");
        FileInputStream input = new FileInputStream(f);
        byte[] content = new byte[(int) f.length()];
        input.read(content);
        input.close();

        Class c = UnsafeWrapper.getUnsafe().defineClass(null, content, 0, content.length, null, null);
        Method m = c.getMethod("test1");
        m.invoke(c.newInstance(), null);
    }
}
