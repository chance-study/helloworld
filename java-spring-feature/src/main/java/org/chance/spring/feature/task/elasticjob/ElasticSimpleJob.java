package org.chance.spring.feature.task.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.chance.elasticjob.annotation.ElasticJobConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

@ElasticJobConfig(name = "elasticSimpleJob", cron = "0/10 * * * * ?",
        shardingItemParameters = "0=0,1=1", description = "简单任务")
public class ElasticSimpleJob implements SimpleJob {

    @Override
    public void execute(ShardingContext shardingContext) {

        System.out.println(2/0);
        String shardParamter = shardingContext.getShardingParameter();
        System.out.println("分片参数："+shardParamter);
        int value = Integer.parseInt(shardParamter);
        for (int i = 0; i < 1000000; i++) {
            if (i % 2 == value) {
                String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
                System.out.println(time + ":开始执行简单任务" + i);
            }
        }

    }

}
