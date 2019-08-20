package org.chance.java.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * LocalContextHolder
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/12
 */
public class LocalContextHolder {

//    private static ThreadLocal<SimpleDateFormat> sdf = new ThreadLocal<>();

//    private static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> null);

    private static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 50; i++) {

            int finalI = i;

            executorService.submit(() -> {
                try {

                    if (finalI < 5) {
                        Thread.sleep(1000 * 5);
                    }
                    if (finalI % 2 == 0) {
                        sdf.set(new SimpleDateFormat("HH:mm:ss"));
                        System.out.println(Thread.currentThread().getName() + ":" + sdf.get().format(new Date()));
                    } else {
                        System.out.println(Thread.currentThread().getName() + ":" + sdf.get().format(new Date()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
        System.out.println("end");
    }

}
