package org.chance.spring.feature.task.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * [Spring Boot系列5-定时任务-springboot整合quartz实现动态定时任务](https://blog.csdn.net/u013042707/article/details/82934725)
 * [@QuartzScheduled](https://gitee.com/liheng/spring-boot-starter-quartz)
 *
 * SampleJob
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/22
 */
public class SampleJob extends QuartzJobBean {

    private String name;

    // Invoked if a Job data map entry with that name
    public void setName(String name) {
        this.name = name;
    }

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println(String.format("Hello %s!", this.name));
    }

}
