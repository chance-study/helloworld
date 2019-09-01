package org.chance.spring.feature.task.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.chance.elasticjob.annotation.ElasticJobConfig;
import org.springframework.context.annotation.Profile;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 任务配置
 * Profile可以选择定时任务开启的环境
 *
 * @author catchance &lt;catchance@163.com&gt;
 * @date 2019-08-30 18:42:57
 */
@ElasticJobConfig(name = "elasticSimpleJob", cron = "0/10 * * * * ?",
        shardingItemParameters = "0=0,1=1", description = "简单任务")
@Slf4j
@Profile("dev")
public class ElasticSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {

        String shardParamter = shardingContext.getShardingParameter();
        log.info("分片参数：{}", shardParamter);
        int value = Integer.parseInt(shardParamter);
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == value) {
                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                log.info(time + ":开始执行简单任务" + i);
            }
        }

    }

}
