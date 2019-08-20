/**
 * ### Unsafe类的作用
 * Unsafe类提供了硬件级别的原子操作，Java无法直接访问到操作系统底层（如系统硬件等)，为此Java使用native方法来扩展Java程序的功能。Java并发包(java.util.concurrent)中大量使用了Unsafe类提供的CAS方法。Unsafe类还提供了阻塞和唤醒线程的方法，以及volatile read/write操作等。
 *
 * ### 获取Unsafe对象
 * UnsafeWrapper
 *
 * ### Unsafe类中的API
 * ---
 * #### 内存管理，直接操作内存的方法
 * UnsafeMemoryDemo
 * #### 动态类加载操作
 * UnsafeDefineClassDemo
 * #### 非常规实例化对象
 * UnsafeAllocateInstanceDemo
 * #### 实例对象字段以及静态属性的操作
 * UnsafeObjectDemo
 * #### 数组操作
 * UnsafeArrayDemo
 * #### 多线程同步
 * //获取持有锁，已不建议使用
 * @Deprecated
 * public native void monitorEnter(Object o);
 * //释放锁，已不建议使用
 * @Deprecated
 * public native void monitorExit(Object o);
 * //尝试获取锁，已不建议使用
 * @Deprecated
 * public native boolean tryMonitorEnter(Object o);
 *
 * #### CAS 操作相关
 * //第一个参数o为给定对象，offset为对象内存的偏移量，通过这个偏移量迅速定位字段并设置或获取该字段的值，
 * //expected表示期望值，x表示要设置的值，下面3个方法都通过CAS原子指令执行操作。
 * public final native boolean compareAndSwapObject(Object o, long offset, Object expected, Object x);
 * public final native boolean compareAndSwapInt(Object o, long offset,int expected,int x);
 * public final native boolean compareAndSwapLong(Object o, long offset,long expected,long x);
 * //以及jdk1.8基于cas操作新增扩展出来的getAndAddInt、getAndAddLong、getAndSetInt、getAndSetLong、getAndSetObject
 * public final int getAndAddInt(Object o, long offset, int delta)
 * public final long getAndAddLong(Object o, long offset, long delta)
 * public final int getAndSetInt(Object o, long offset, int newValue)
 * public final long getAndSetLong(Object o, long offset, long newValue)
 * public final Object getAndSetObject(Object o, long offset, Object newValue)
 *
 * #### 挂起与恢复
 * //线程调用该方法，线程将一直阻塞直到超时，或者是中断条件出现。
 * public native void park(boolean isAbsolute, long time);
 *
 * //终止挂起的线程，恢复正常.java.util.concurrent包中挂起操作都是在LockSupport类实现的，其底层正是使用这两个方法，
 * public native void unpark(Object thread);
 *
 * #### 内存屏障
 * //在该方法之前的所有读操作，一定在load屏障之前执行完成
 * public native void loadFence();
 * //在该方法之前的所有写操作，一定在store屏障之前执行完成
 * public native void storeFence();
 * //在该方法之前的所有读写操作，一定在full屏障之前执行完成，这个内存屏障相当于上面两个的合体功能
 * public native void fullFence();
 *
 * #### 包装受检异常为运行时异常
 * public native void throwException(Throwable var1);
 *
 * #### Info 仅仅是返回一个低级别的内存相关的信息
 * //获取JVM的地址空间大小，4或者8字节
 * public native int addressSize();
 * //获取本机内存的页数，一般是4096字节
 * public native int pageSize();
 *
 * #### 看系统平均负载
 * //获取系统的平均负载值，loadavg这个double数组将会存放负载值的结果，nelems决定样本数量，nelems只能取值为1到3，
 * //分别代表最近1、5、15分钟内系统的平均负载。如果无法获取系统的负载，此方法返回-1，否则返回获取到的样本数量(loadavg中有效的元素个数)。
 * public native int getLoadAverage(double[] loadavg, int nelems);
 */
package org.chance.java.unsafe;