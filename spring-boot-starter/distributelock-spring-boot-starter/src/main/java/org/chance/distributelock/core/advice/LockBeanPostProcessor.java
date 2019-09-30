package org.chance.distributelock.core.advice;

import org.chance.distributelock.api.annotation.Locked;
import org.chance.distributelock.api.key.KeyGenerator;
import org.chance.distributelock.core.converter.IntervalConverter;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.TaskScheduler;

/**
 * LockBeanPostProcessor
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
public class LockBeanPostProcessor extends AbstractAdvisingBeanPostProcessor implements InitializingBean {

    private final IntervalConverter intervalConverter;
    private final LockTypeResolver lockTypeResolver;
    private final KeyGenerator keyGenerator;
    private final TaskScheduler taskScheduler;

    public LockBeanPostProcessor(IntervalConverter intervalConverter, LockTypeResolver lockTypeResolver, KeyGenerator keyGenerator, TaskScheduler taskScheduler) {
        this.intervalConverter = intervalConverter;
        this.lockTypeResolver = lockTypeResolver;
        this.keyGenerator = keyGenerator;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final AnnotationMatchingPointcut pointcut = new AnnotationMatchingPointcut(null, Locked.class, true);
        final LockMethodInterceptor interceptor = new LockMethodInterceptor(keyGenerator, lockTypeResolver, intervalConverter, taskScheduler);

        this.advisor = new DefaultPointcutAdvisor(pointcut, interceptor);
    }
}
