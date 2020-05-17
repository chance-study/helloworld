package org.chance.java.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * InterruptDemo
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/10/11
 */

@Slf4j
public class InterruptDemo {

    /**
     * 1、java.lang.Thread#interrupt
     * 中断目标线程，给目标线程发一个中断信号，线程被打上中断标记。
     * 2、java.lang.Thread#isInterrupted()
     * 判断目标线程是否被中断，不会清除中断标记。
     * 3、java.lang.Thread#interrupted
     * 判断目标线程是否被中断，会清除中断标记。
     */

    public static void main(String[] args) throws InterruptedException {

        log.info("=== start ===");

//        demo1();
//        demo2();
        demo3();

        log.info("=== end ===");

    }

    private static void demo3() throws InterruptedException {

        Thread thread = new Thread(()->{
            while (true) {
                if(Thread.currentThread().isInterrupted()) {
                    log.info(" demo3 interrupt");
                    return;
                }

                log.info("--- run ---");

                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                    log.error(e.getMessage());

                    // 捕获中断异常后，需要再次中断才能结束线程
                    Thread.currentThread().interrupt();

                }

            }
        }, "demo3");

        thread.start();

        Thread.sleep(2000);

        thread.interrupt();

    }

    private static void demo1() {
        Thread thread = new Thread(() -> {
            while (true) {
                log.info("---- run ---- ");
                Thread.yield();
            }
        }, "demo1");

        thread.start();

        // 中断不起作用
        thread.interrupt();
    }

    public static void demo2() {

        Thread thread = new Thread(()->{

            while (true) {

                Thread.yield();

                if (Thread.currentThread().isInterrupted()) {
                    log.info("demo2 thread interrupt");
                    return;
                }
            }

        }, "demo2");

        thread.start();
        thread.interrupt();

    }

}
