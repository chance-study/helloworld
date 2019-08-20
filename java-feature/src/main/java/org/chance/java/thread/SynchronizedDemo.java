package org.chance.java.thread;

/**
 * SynchronizedDemo
 *
 * public class org.chance.java.thread.SynchronizedDemo {
 *   public org.chance.java.thread.SynchronizedDemo();
 *     Code:
 *        0: aload_0
 *        1: invokespecial #1                  // Method java/lang/Object."<init>":()V
 *        4: return
 *     LineNumberTable:
 *       line 10: 0
 *     LocalVariableTable:
 *       Start  Length  Slot  Name   Signature
 *           0       5     0  this   Lorg/chance/java/thread/SynchronizedDemo;
 *
 *   public static void main(java.lang.String[]);
 *     Code:
 *        0: ldc           #2                  // class org/chance/java/thread/SynchronizedDemo
 *        2: dup
 *        3: astore_1
 *        4: monitorenter  // 执行同步代码块后首先要先执行monitorenter指令 使用Synchronized进行同步，其关键就是必须要对对象的监视器monitor进行获取，当线程获取monitor后才能继续往下执行，否则就只能等待。而这个获取的过程是互斥的，即同一时刻只有一个线程能够获取到monitor
 *        5: aload_1
 *        6: monitorexit   // 退出的时候monitorexit指令
 *        7: goto          15
 *       10: astore_2
 *       11: aload_1
 *       12: monitorexit   // 退出的时候monitorexit指令 执行静态同步方法的时候就只有一条monitorexit指令，并没有monitorenter获取锁的指令。这就是锁的重入性，即在同一锁程中，线程不需要再次获取同一把锁。Synchronized先天具有重入性。每个对象拥有一个计数器，当线程获取该对象锁后，计数器就会加一，释放锁后就会将计数器减一。
 *       13: aload_2
 *       14: athrow
 *       15: invokestatic  #3                  // Method method:()V
 *       18: return
 *     Exception table:
 *        from    to  target type
 *            5     7    10   any
 *           10    13    10   any
 *     LineNumberTable:
 *       line 13: 0
 *       line 14: 5
 *       line 15: 15
 *       line 16: 18
 *     LocalVariableTable:
 *       Start  Length  Slot  Name   Signature
 *           0      19     0  args   [Ljava/lang/String;
 * }
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/13
 */
public class SynchronizedDemo {

    public static void main(String[] args) {
        synchronized (SynchronizedDemo.class) {
        }
        method();
    }

    private static void method() {
    }

}
