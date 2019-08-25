package org.chance.spring.feature.task.timer;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

@Slf4j
public class TimerDemo {

    public static void main(String[] args) {
        Timer timer = new Timer();

        /*
         * scheduleAtFixedRate和schedule的区别：
         * scheduleAtFixedRate会尽量减少漏掉调度的情况，如果前一次执行时间过长，导致一个或几个任务漏掉了，那么会补回来
         * schedule过去的不会补，直接加上间隔时间执行下一次任务。
         *
         * 同一个Timer下添加多个TimerTask，如果其中一个没有捕获抛出的异常，则全部任务都会终止运行。
         * 但是多个Timer是互不影响
         *
         */
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                log.info("timer demo {}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        }, 1000, 5000);  // 延时1s 每隔5s执行一次


    }

}
