package org.chance.util.concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-09-16 14:28:58
 */
public class CountDownLatchDemo {

    private static class Client {
        public static void main(String[] args) throws InterruptedException {

            //场景1测试代码块开始
            CountDownLatch latch = new CountDownLatch(2);

            for (int i = 0; i < 2; i++) {
                Thread thread = new Thread(() -> {
                    try {
                        Thread.sleep(new Random().nextInt(10) * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName());
                    latch.countDown();
                });
                thread.start();
            }

            System.out.println("main thread begin...");
            latch.await(); //使当前线程在锁存器倒计数至零之前一直等待
            System.out.println("main thread end....");
            //场景1测试代码块结束
            System.out.println("===============长长的分割线================");

        }
    }


    private static class Client1 {

        public static void main(String[] args) throws InterruptedException {

            //场景2测试代码块开始
            CountDownLatch begin = new CountDownLatch(1);
            for (int i = 0; i < 2; i++) {
                Thread thread = new Thread(() -> {
                    try {
                        begin.await();
                        System.out.println(Thread.currentThread().getName() + " 起跑成功!");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.println("main thread begin...");
            begin.countDown();
            System.out.println("main thread end....");
            //场景2测试代码块结束

        }

    }

}
