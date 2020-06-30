package org.chance.spring.feature.task.quartz.config;

import org.chance.spring.feature.task.quartz.SampleJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * QuartzConfig
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/22
 */
@Configuration
@Profile("dev")
public class QuartzConfig {

    @Bean
    public JobDetail sampleJobDetail() {
        return JobBuilder
                .newJob(SampleJob.class)
                .withIdentity("sampleJob", "sample")
                //JobDataMap可以给任务execute传递参数
                .usingJobData("name", "World")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger sampleJobTrigger() {

        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(2)
                .repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(sampleJobDetail())
                .withIdentity("sampleTrigger", "sample")
                .usingJobData("job_trigger_param","job_trigger_param1")
                .startNow()
//                .withSchedule(scheduleBuilder)
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * * * ? *"))
                .build();
    }
}
