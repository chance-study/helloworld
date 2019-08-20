package org.chance.java.unsafe;

import lombok.extern.slf4j.Slf4j;
import org.chance.wrapper.UnsafeWrapper;
import sun.misc.Unsafe;

/**
 * UnsafeObjectDemo
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/14
 */
@Slf4j
public class UnsafeObjectDemo {

    public static void main(String[] args) throws NoSuchFieldException {

        /**
         * //获取字段f在实例对象中的偏移量
         * public native long objectFieldOffset(Field f);
         * //静态属性的偏移量，用于在对应的Class对象中读写静态属性
         * public native long staticFieldOffset(Field f);
         * //返回值就是f.getDeclaringClass()
         * public native Object staticFieldBase(Field f);
         *
         * //获得给定对象偏移量上的int值，所谓的偏移量可以简单理解为指针指向该变量的内存地址，
         * //通过偏移量便可得到该对象的变量，进行各种操作
         * public native int getInt(Object o, long offset);
         * //设置给定对象上偏移量的int值
         * public native void putInt(Object o, long offset, int x);
         * //设置给定对象的int值，使用volatile语义，即设置后立马更新到内存对其他线程可见
         * public native void  putIntVolatile(Object o, long offset, int x);
         * //获得给定对象的指定偏移量offset的int值，使用volatile语义，能获取到最新的int值。
         * public native int getIntVolatile(Object o, long offset);
         * //这是一个有延迟的方法，使用这个方法写入volatile对象只能够避免写重排序，但使volatile失去可见性,不保证值的改变被其他线程立即看到，这样的技巧可以在某些场景下提高效率。
         * public native void putOrderedInt(Object o,long offset,int x);
         *
         * //其他基本数据类型(long,char,byte,float,double)的操作与getInt、putInt、putIntVolatile、getIntVolatile、putOrderedInt相同，引用类型Object也一样。
         *
         * //将指定对象的给定offset偏移量内存块中的所有字节设置为固定值
         * public native void setMemory(Object o, long offset, long bytes, byte value);
         * //将给定offset偏移量内存块中的所有字节bytes设置为固定值value
         * public void setMemory(long offset, long bytes, byte value);
         * //从srcObj对象的srcOffset位置开始复制bytes个字节到destObj（从destOffset开始）
         * public native void copyMemory(Object srcObj, long srcOffset, Object destObj, long destOffset, long bytes);
         * //从srcOffset复制bytes个字节到destOffset开始的内存块
         * public void copyMemory(long srcOffset, long destOffset, long bytes)
         */

        Unsafe unsafe = UnsafeWrapper.getUnsafe();
        int h = 5, w = 6;

        Rectangle rectangle = new Rectangle();

        //获取对象字段的偏移位置
        long hObjectFieldOffset = unsafe.objectFieldOffset(Rectangle.class.getDeclaredField("h"));
        long wObjectFieldOffset = unsafe.objectFieldOffset(Rectangle.class.getDeclaredField("w"));
        log.info("hObjectFieldOffset:{}, wObjectFieldOffset:{}", hObjectFieldOffset, wObjectFieldOffset);
        //设置对象上的字段值
        unsafe.putOrderedInt(rectangle, hObjectFieldOffset, h);
        unsafe.putInt(rectangle, wObjectFieldOffset, w);
        //获取对象上的字段值
        log.info("get rectangle.h:{}, h:{}", unsafe.getInt(rectangle, hObjectFieldOffset), h);
        log.info("get rectangle.w:{}, h:{}", unsafe.getInt(rectangle, wObjectFieldOffset), w);

        Rectangle subRectangle = new Rectangle(h, w, null);

        long subRectangleObjectFieldOffset = unsafe.objectFieldOffset(Rectangle.class.getDeclaredField("subRectangle"));

        log.info("subRectangleObjectFieldOffset:{}", subRectangleObjectFieldOffset);//设置对象上的字段值
        unsafe.putObject(rectangle, subRectangleObjectFieldOffset, subRectangle);
        //获取对象上的字段值
        log.info("get subRectangle:{}", unsafe.getObject(rectangle, subRectangleObjectFieldOffset));

        int count = 4;
        //获取静态字段的偏移位置
        log.info("staticFieldBase count:{} ", unsafe.staticFieldBase(Rectangle.class.getDeclaredField("count")));
        long staticFieldOffset = unsafe.staticFieldOffset(Rectangle.class.getDeclaredField("count"));
        log.info("staticFieldOffset:{}", staticFieldOffset);
        //设置类静态字段得值
        unsafe.putInt(Rectangle.class, staticFieldOffset, count);
        //获取类静态字段得值
        log.info("get Rectangle.count:{}, count:{}", unsafe.getInt(Rectangle.class, staticFieldOffset), count);

        unsafe.setMemory(rectangle, hObjectFieldOffset, 8, (byte) 0);
        log.info("get rectangle.h:{}, get rectangle.w:{}", unsafe.getInt(rectangle, hObjectFieldOffset), unsafe.getInt(rectangle, wObjectFieldOffset));


    }

}
