package org.chance.spring.feature.task.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/22
 */

@Slf4j
//@Component
public class SaticScheduleTask {

    /**
     * 基于注解@Scheduled默认为单线程，开启多个任务时，任务的执行时机会受上一个任务执行时间的影响。
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void execute() {
        log.info("execute job");
    }

}
