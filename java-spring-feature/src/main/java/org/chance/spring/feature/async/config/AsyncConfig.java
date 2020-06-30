package org.chance.spring.feature.async.config;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步方法建议尽量处理耗时的任务，或者是处理不希望阻断主流程的任务（比如异步记录操作日志）
 * 对应的@Enable注解，最好写在属于自己的配置文件上，保持内聚性
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-06-28 16:17:44
 */
@EnableAsync
@Configuration
@Profile("async")
public class AsyncConfig implements AsyncConfigurer {

    /**
     *
     * 尽量为@Async准备一个专门的线程池来提高效率减少开销，而不要用Spring默认的SimpleAsyncTaskExecutor，它不是一个真正的线程池~
     *
     * @return
     */
    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        /**
         * Runtime.getRuntime().availableProcessors()
         *
         * 核心线程数
         * CPU密集型任务: 尽量使用较小的线程池，一般为CPU核心数+1。(+1是为了利用等待空闲)
         * IO密集型任务: 可以使用稍大的线程池，一般为CPU总核心数 * 2 +1核心数。
         *
         * 最佳线程数目 = （（线程等待时间+线程CPU时间）/线程CPU时间 ）* CPU数目
         * 比如平均每个线程CPU运行时间为0.5s，而线程等待时间（非CPU运行时间，比如IO）为1.5s，CPU核心数为8，那么根据上面这个公式估算得到：((0.5+1.5)/0.5)*8=32。
         */
        executor.setCorePoolSize(10);
        /**
         * 最大线程数
         */
        executor.setMaxPoolSize(20);
        /**
         * 队列大小
         */
        executor.setQueueCapacity(1000);
        /**
         * 线程最大空闲时间
         */
        executor.setKeepAliveSeconds(300);
        /**
         * 指定用于新创建的线程名称的前缀。
         */
        executor.setThreadNamePrefix("Async-Executor-");
        /**
         * 拒绝策略（一共四种，此处省略）
         */
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        /**
         * 这一步千万不能忘了，否则报错： java.lang.IllegalStateException: ThreadPoolTaskExecutor not initialized
         */
        executor.initialize();

        return executor;

    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}
