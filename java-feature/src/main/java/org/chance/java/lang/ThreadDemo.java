package org.chance.java.lang;

/**
 * ThreadDemo
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/15
 */
public class ThreadDemo {

    static final class JavaYieldExp extends Thread {

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                Thread.yield();
                System.out.println(Thread.currentThread().getName() + " in control");
            }
        }
    }

    public static void main(String[] args) {

        /**
         * yield 不会释放锁
         *
         * 线程执行了yield()方法后，就会进入Runnable(就绪状态)，【不同于sleep()和join（）方法，因为这两个方法是使线程进入阻塞状态】。除此之外，yield()方法还与线程优先级有关，当某个线程调用yield()方法时，就会从运行状态转换到就绪状态后，CPU从就绪状态线程队列中只会选择与该线程优先级相同或者更高优先级的线程去执行。
         */

        JavaYieldExp t1 = new JavaYieldExp();
        JavaYieldExp t2 = new JavaYieldExp();

        t1.setPriority(1);
        // 设置优先级
        t2.setPriority(10);

        // this will call run() method
        t1.start();
        t2.start();

        for (int i = 0; i < 3; i++) {
            // Control passes to child thread
            Thread.yield();
            System.out.println(Thread.currentThread().getName() + " in control");
        }

        Thread.yield();

    }

}


