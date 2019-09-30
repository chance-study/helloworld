package org.chance.distributelock.redis.impl;

import org.assertj.core.data.Offset;
import org.chance.distributelock.api.Lock;
import org.chance.distributelock.embedded.EmbeddedRedis;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *使用@DirtiesContext，可以保证每个test class的执行上下文的独立性、隔离性，但是也会有让测试运行速度变慢的副作用。
 */
@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SimpleRedisLockTest implements InitializingBean {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    private StringRedisTemplate redisTemplate;

    private Lock lock;

    @Override
    public void afterPropertiesSet() {
        lock = new SimpleRedisLock(redisTemplate, () -> "abc");
    }

    @Before
    public void cleanRedis() {
        redisTemplate.execute((RedisCallback<?>) connection -> {
            connection.flushDb();
            return null;
        });
    }

    @Test
    public void shouldLock() {
        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(token).isEqualTo("abc");
        assertThat(redisTemplate.opsForValue().get("locks:1")).isEqualTo("abc");
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
    }

    @Test
    public void shouldNotLock() {
        redisTemplate.opsForValue().set("locks:1", "def");
        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(token).isNull();
        assertThat(redisTemplate.opsForValue().get("locks:1")).isEqualTo("def");
    }

    @Test
    public void shouldRelease() {
        redisTemplate.opsForValue().set("locks:1", "abc");
        lock.release(Collections.singletonList("1"), "locks", "abc");
        assertThat(redisTemplate.opsForValue().get("locks:1")).isNull();
    }

    @Test
    public void shouldNotRelease() {
        redisTemplate.opsForValue().set("locks:1", "def");
        lock.release(Collections.singletonList("1"), "locks", "abc");
        assertThat(redisTemplate.opsForValue().get("locks:1")).isEqualTo("def");
    }

    @Test
    public void shouldRefresh() throws InterruptedException {
        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
        Thread.sleep(500);
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(500, Offset.offset(100L));
        assertThat(lock.refresh(Collections.singletonList("1"), "locks", token, 1000)).isTrue();
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
    }

    @Test
    public void shouldNotRefreshBecauseKeyExpired() {
        assertThat(lock.refresh(Collections.singletonList("1"), "locks", "abc", 1000)).isFalse();
        assertThat(redisTemplate.keys("*")).isNullOrEmpty();
    }

    @Test
    public void shouldNotRefreshBecauseTokenDoesNotMatch() throws InterruptedException {
        lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
        Thread.sleep(500);
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(500, Offset.offset(100L));
        assertThat(lock.refresh(Collections.singletonList("1"), "locks", "wrong-token", 1000)).isFalse();
        Thread.sleep(500);
        assertThat(redisTemplate.opsForValue().get("locks:1")).isNull();
    }

    @Test
    public void shouldExpire() throws InterruptedException {
        lock.acquire(Collections.singletonList("1"), "locks", 100);
        Thread.sleep(100);
        assertThat(redisTemplate.opsForValue().get("locks:1")).isNull();
    }

    @SpringBootApplication(scanBasePackageClasses = EmbeddedRedis.class)
    static class TestApplication {

    }
}