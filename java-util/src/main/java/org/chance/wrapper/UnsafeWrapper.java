package org.chance.wrapper;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author GengChao &lt; catchance@163.com &gt;
 * @date 2019/8/14
 */
public class UnsafeWrapper {

    private static Unsafe unsafe;

    public static Unsafe getUnsafe() {
        if (unsafe == null) {
            try {
                // 通过反射得到unsafe对应的Field对象
                Field field = Unsafe.class.getDeclaredField("theUnsafe");
                // 设置该Field为可访问
                field.setAccessible(true);
                // 通过Field得到该Field对应的具体对象，传入null是因为该Field为static的
                unsafe = (Unsafe) field.get(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return unsafe;
    }

}
