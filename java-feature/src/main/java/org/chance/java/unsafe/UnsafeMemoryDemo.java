package org.chance.java.unsafe;

import lombok.extern.slf4j.Slf4j;
import org.chance.wrapper.UnsafeWrapper;
import sun.misc.Unsafe;

/**
 * UnsafeMemoryDemo
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/14
 */

@Slf4j
public class UnsafeMemoryDemo {

    public static void main(String[] args) {

        /*
         * [sun.misc.Unsafe对象学习](https://www.jianshu.com/p/b5e8f48ae0ba)
         * Unsafe类中的API 内存管理，直接操作内存的方法
         *
         * //分配指定大小的内存
         * public native long allocateMemory(long bytes);
         * //重新分配 (扩展) 一块内存 (之前分配内存当然会被GC回收掉). 第一个参数为原内存地址,返回新的内存地址. 原内存中的内容会迁移到新开辟的内存中.
         * public native long reallocateMemory(long address, long bytes);
         * //用于释放allocateMemory和reallocateMemory申请的内存
         * public native void freeMemory(long address);
         * //设置给定内存地址的值
         * public native void putAddress(long address, long x);
         * //获取指定内存地址的值
         * public native long getAddress(long address);
         *
         * //设置指定内存的byte值
         * public native byte  getByte(long address);
         * //设置指定内存的byte值
         * public native void  putByte(long address, byte x);
         * //其他基本数据类型(long,char,float,double,short等)的操作与putByte及getByte相同
         *
         */

        Unsafe unsafe = UnsafeWrapper.getUnsafe();
        int size = 16;
        int data = 1024;
        long memoryAddress = unsafe.allocateMemory(size);
        //直接往内存写入数据
        unsafe.putAddress(memoryAddress, data);
        //获取指定内存地址的数据
        log.info("getAddress:{}, data:{}", unsafe.getAddress(memoryAddress), data);

        //putLong和putAddress效果一样
        data = 2 * data;
        unsafe.putLong(memoryAddress, data);
        unsafe.putAddress(memoryAddress + 8, data * 2);
        unsafe.putAddress(memoryAddress + 16, data * 4);
        unsafe.putAddress(memoryAddress + 24, data * 8);
        log.info("getAddress:{}, data:{}", unsafe.getAddress(memoryAddress), data);

        //调换顺序报错
        long reallocateMemoryAddress = unsafe.reallocateMemory(memoryAddress, size * 1024);
        log.info("memoryAddress:{}, reallocateMemoryAddress:{}", memoryAddress, reallocateMemoryAddress);

        log.info("getAddress:{}, data:{}", unsafe.getAddress(reallocateMemoryAddress), data);
        log.info("getAddress+8:{}, data:{}", unsafe.getAddress(reallocateMemoryAddress + 8), data * 2);
        log.info("getAddress+16:{}, data:{}", unsafe.getAddress(reallocateMemoryAddress + 16), data * 4);
        log.info("getAddress+24:{}, data:{}", unsafe.getAddress(reallocateMemoryAddress + 24), data * 8);
        log.info("getAddress+32:{}, data:{}", unsafe.getAddress(reallocateMemoryAddress + 32), data * 16);

        unsafe.freeMemory(reallocateMemoryAddress);
    }

}
