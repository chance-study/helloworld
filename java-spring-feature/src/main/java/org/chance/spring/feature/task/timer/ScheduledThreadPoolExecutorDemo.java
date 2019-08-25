package org.chance.spring.feature.task.timer;


import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ScheduledThreadPoolExecutorDemo {

    public static void main(String[] args) {

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5);

        executorService.scheduleWithFixedDelay(()->{
            log.info("scheduled executor demo");
        }, 1, 2, TimeUnit.SECONDS);

    }

}
