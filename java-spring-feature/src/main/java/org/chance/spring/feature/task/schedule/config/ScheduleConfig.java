package org.chance.spring.feature.task.schedule.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * ScheduleConfig
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/22
 */

@Slf4j
@Configuration //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling // 2.开启定时任务
@Profile("dev")
public class ScheduleConfig {

}
