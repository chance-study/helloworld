package org.chance.java.unsafe;

import lombok.extern.slf4j.Slf4j;
import org.chance.wrapper.UnsafeWrapper;
import sun.misc.Unsafe;

/**
 * UnsafeArrayDemo
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/14
 */
@Slf4j
public class UnsafeArrayDemo {

    public static void main(String[] args) {

        /**
         * //获取数组第一个元素的偏移地址
         * public native int arrayBaseOffset(Class arrayClass);
         * //数组中一个元素占据的内存空间,arrayBaseOffset与arrayIndexScale配合使用，可定位数组中每个元素在内存中的位置
         * public native int arrayIndexScale(Class arrayClass);
         */

        Unsafe unsafe = UnsafeWrapper.getUnsafe();

        Rectangle rectangle = new Rectangle(5, 6, null);
        Object[] array = new Object[] {rectangle, rectangle, rectangle};

        //获取数组第一个元素的偏移地址
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        //获取JVM的地址空间大小
        int addressSize = unsafe.addressSize();
        //获取对象地址
        long objectAddress;
        switch (addressSize)
        {
            case 4://32位jvm
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8://64位jvm
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("unsupported address size: " + addressSize);
        }
        log.info("array baseOffset:{}, addressSize:{}, objectAddress:{}", baseOffset, addressSize, objectAddress);

        log.info("");
        log.info("---------------------------");

        log.info("boolean[].class arrayBaseOffset:{}", unsafe.arrayBaseOffset(boolean[].class));
        log.info("byte[].class arrayBaseOffset:{}", unsafe.arrayBaseOffset(byte[].class));
        log.info("short[].class arrayBaseOffset:{}", unsafe.arrayBaseOffset(short[].class));
        log.info("char[].class arrayBaseOffset:{}", unsafe.arrayBaseOffset(char[].class));
        log.info("int[].class arrayBaseOffset:{}", unsafe.arrayBaseOffset(int[].class));
        log.info("long[].class arrayBaseOffset:{}", unsafe.arrayBaseOffset(long[].class));
        log.info("float[].class arrayBaseOffset:{}", unsafe.arrayBaseOffset(float[].class));
        log.info("double[].class arrayBaseOffset:{}", unsafe.arrayBaseOffset(double[].class));
        log.info("Object[].class arrayBaseOffset:{}", unsafe.arrayBaseOffset(Object[].class));
        log.info("boolean[].class arrayIndexScale:{}", unsafe.arrayIndexScale(boolean[].class));
        log.info("byte[].class arrayIndexScale:{}", unsafe.arrayIndexScale(byte[].class));
        log.info("short[].class arrayIndexScale:{}", unsafe.arrayIndexScale(short[].class));
        log.info("char[].class arrayIndexScale:{}", unsafe.arrayIndexScale(char[].class));
        log.info("int[].class arrayIndexScale:{}", unsafe.arrayIndexScale(int[].class));
        log.info("long[].class arrayIndexScale:{}", unsafe.arrayIndexScale(long[].class));
        log.info("float[].class arrayIndexScale:{}", unsafe.arrayIndexScale(float[].class));
        log.info("double[].class arrayIndexScale:{}", unsafe.arrayIndexScale(double[].class));
        log.info("Object[].class arrayIndexScale:{}", unsafe.arrayIndexScale(Object[].class));


    }

}
