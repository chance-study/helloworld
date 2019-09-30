package org.chance.distributelock.core.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.chance.distributelock.api.Lock;
import org.chance.distributelock.api.annotation.Locked;
import org.chance.distributelock.api.exception.DistributedLockException;
import org.chance.distributelock.api.key.KeyGenerator;
import org.chance.distributelock.core.converter.IntervalConverter;
import org.chance.distributelock.core.exception.LockNotAvailableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.CompositeRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * LockMethodInterceptor
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
public class LockMethodInterceptor implements MethodInterceptor {

    private static final Logger log = LoggerFactory
            .getLogger(LockMethodInterceptor.class);

    private final KeyGenerator keyGenerator;
    private final LockTypeResolver lockTypeResolver;
    private final IntervalConverter intervalConverter;
    private final TaskScheduler taskScheduler;

    public LockMethodInterceptor(KeyGenerator keyGenerator, LockTypeResolver lockTypeResolver, IntervalConverter intervalConverter, TaskScheduler taskScheduler) {
        this.keyGenerator = keyGenerator;
        this.lockTypeResolver = lockTypeResolver;
        this.intervalConverter = intervalConverter;
        this.taskScheduler = taskScheduler;
    }

    @Override
    public Object invoke(final MethodInvocation invocation) throws Throwable {
        final Method method = AopUtils.getMostSpecificMethod(invocation.getMethod(), invocation.getThis().getClass());
        final Locked locked = AnnotatedElementUtils.findMergedAnnotation(method, Locked.class);

        final Lock lock = lockTypeResolver.get(locked.type());

        if (lock == null) {
            throw new DistributedLockException(String.format("Lock type %s not configured", locked.type().getName()));
        }

        if (StringUtils.isEmpty(locked.expression())) {
            throw new DistributedLockException("Missing expression: " + locked);
        }

        final List<String> keys;
        try {
            keys = keyGenerator.resolveKeys(locked.prefix(), locked.expression(), invocation.getThis(), method, invocation.getArguments());
        } catch (final RuntimeException e) {
            throw new DistributedLockException("Cannot resolve keys to lock from expression: " + locked.expression(), e);
        }

        final long expiration = intervalConverter.toMillis(locked.expiration());
        String token = null;
        ScheduledFuture<?> scheduledFuture = null;
        try {
            try {
                token = constructRetryTemplate(locked).execute(context -> {
                    final String attemptedToken = lock.acquire(keys, locked.storeId(), expiration);

                    if (StringUtils.isEmpty(attemptedToken)) {
                        throw new LockNotAvailableException(String.format("Lock not available for keys: %s in store %s", keys, locked.storeId()));
                    }

                    return attemptedToken;
                });
            } catch (final Exception e) {
                throw new DistributedLockException("Unable to acquire lock with expression: " + locked.expression(), e);
            }

            log.debug("Acquired lock for keys {} with token {} in store {}", keys, token, locked.storeId());

            final long refresh = intervalConverter.toMillis(locked.refresh());
            if (refresh > 0) {
                final LockRefreshRunnable lockRefreshRunnable = new LockRefreshRunnable(lock, keys, locked.storeId(), token, expiration);
                scheduledFuture = taskScheduler.scheduleAtFixedRate(lockRefreshRunnable, refresh);
            }

            return invocation.proceed();
        } finally {
            if (scheduledFuture != null && !scheduledFuture.isCancelled() && !scheduledFuture.isDone()) {
                scheduledFuture.cancel(true);
            }

            if (token != null && !locked.manuallyReleased()) {
                final boolean released = lock.release(keys, locked.storeId(), token);
                if (released) {
                    log.debug("Released lock for keys {} with token {} in store {}", keys, token, locked.storeId());
                } else {
                    // this could indicate that locks are released before method execution is finished and that locks expire too soon
                    // this could also indicate a problem with the store where locks are held, connectivity issues or query problems
                    log.error("Couldn't release lock for keys {} with token {} in store {}", keys, token, locked.storeId());
                }
            }
        }
    }

    private RetryTemplate constructRetryTemplate(final Locked locked) {
        // how long to sleep between retries
        final FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
        fixedBackOffPolicy.setBackOffPeriod(intervalConverter.toMillis(locked.retry()));

        // when to timeout the whole operation
        final TimeoutRetryPolicy timeoutRetryPolicy = new TimeoutRetryPolicy();
        timeoutRetryPolicy.setTimeout(intervalConverter.toMillis(locked.timeout()));

        // what exceptions to retry; only LockNotAvailableException, all other exceptions are unexpected and locking should fail
        final SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy(Integer.MAX_VALUE, Collections.singletonMap(LockNotAvailableException.class, true));

        // combine policies
        final CompositeRetryPolicy compositeRetryPolicy = new CompositeRetryPolicy();
        compositeRetryPolicy.setPolicies(new RetryPolicy[]{timeoutRetryPolicy, simpleRetryPolicy});

        // construct the template
        final RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(compositeRetryPolicy);
        retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

        return retryTemplate;
    }

}
