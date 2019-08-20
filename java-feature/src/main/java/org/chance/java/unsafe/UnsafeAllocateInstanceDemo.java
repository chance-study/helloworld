package org.chance.java.unsafe;

import lombok.extern.slf4j.Slf4j;
import org.chance.wrapper.UnsafeWrapper;
import sun.misc.Unsafe;

/**
 * UnsafeAllocateInstanceDemo
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/14
 */
@Slf4j
public class UnsafeAllocateInstanceDemo {
    public static void main(String[] args) throws InstantiationException {

        /**
         * //传入一个对象的class并创建该实例对象，但不会调用构造方法
         * public native Object allocateInstance(Class cls) throws InstantiationException;
         */
        Unsafe unsafe = UnsafeWrapper.getUnsafe();
        Rectangle rectangle = (Rectangle) unsafe.allocateInstance(Rectangle.class);
        log.info("func=unsafeAllocateInstanceTest rectangle:{}", rectangle);

    }
}
