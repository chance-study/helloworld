package org.chance.spring.feature.task.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/22
 */
@Slf4j
public class SampleJobListener implements JobListener {

    /**
     * 用于获取该JobListener的名称。
     * @return
     */
    @Override
    public String getName() {
        return "SampleJobListener";
    }

    /**
     * Scheduler在JobDetail将要被执行时调用这个方法。
     * @param context
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        log.info(jobName + " is going to be executed");
    }

    /**
     * Scheduler在JobDetail即将被执行，但又被TriggerListerner否决时会调用该方法
     * @param context
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        String jobName = context.getJobDetail().getKey().getName();
        log.info(jobName + "  was vetoed and not executed");
    }

    /**
     * Scheduler在JobDetail被执行之后调用这个方法
     * @param context
     * @param jobException
     */
    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobName = context.getJobDetail().getKey().getName();
        log.info(jobName + " was executed");
    }
}
