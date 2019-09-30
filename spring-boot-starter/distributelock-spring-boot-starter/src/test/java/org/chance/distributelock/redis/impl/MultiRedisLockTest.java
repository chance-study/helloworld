package org.chance.distributelock.redis.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
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

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MultiRedisLockTest implements InitializingBean {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // false IntelliJ warning
    private StringRedisTemplate redisTemplate;

    private Lock lock;

    @Override
    public void afterPropertiesSet() {
        lock = new MultiRedisLock(redisTemplate, () -> "abc");
    }

    @Before
    public void cleanRedis() {
        redisTemplate.execute((RedisCallback<?>) connection -> {
            connection.flushDb();
            return null;
        });
    }

    @Test
    public void shouldLockSingleKey() {
        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(token).isEqualTo("abc");
        assertThat(redisTemplate.opsForValue().get("locks:1")).isEqualTo("abc");
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
    }

    @Test
    public void shouldLockMultipleKeys() {
        final String token = lock.acquire(Arrays.asList("1", "2"), "locks", 1000);
        assertThat(token).isEqualTo("abc");
        assertThat(redisTemplate.opsForValue().get("locks:1")).isEqualTo("abc");
        assertThat(redisTemplate.opsForValue().get("locks:2")).isEqualTo("abc");
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
        assertThat(redisTemplate.getExpire("locks:2", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
    }

    @Test
    public void shouldNotLockWhenWholeLockIsTaken() {
        redisTemplate.opsForValue().set("locks:1", "def");
        redisTemplate.opsForValue().set("locks:2", "ghi");
        final String token = lock.acquire(Arrays.asList("1", "2"), "locks", 1000);
        assertThat(token).isNull();
        assertThat(redisTemplate.opsForValue().get("locks:1")).isEqualTo("def");
        assertThat(redisTemplate.opsForValue().get("locks:2")).isEqualTo("ghi");
    }

    @Test
    public void shouldNotLockWhenLockIsPartiallyTaken() {
        redisTemplate.opsForValue().set("locks:1", "def");
        final String token = lock.acquire(Arrays.asList("1", "2"), "locks", 1000);
        assertThat(token).isNull();
        assertThat(redisTemplate.opsForValue().get("locks:1")).isEqualTo("def");
        assertThat(redisTemplate.opsForValue().get("locks:2")).isNull();
    }

    @Test
    public void shouldReleaseSingleKey() {
        redisTemplate.opsForValue().set("locks:1", "abc");
        lock.release(Collections.singletonList("1"), "locks", "abc");
        assertThat(redisTemplate.opsForValue().get("locks:1")).isNull();
    }

    @Test
    public void shouldReleaseMultipleKeys() {
        redisTemplate.opsForValue().set("locks:1", "abc");
        redisTemplate.opsForValue().set("locks:2", "abc");
        lock.release(Arrays.asList("1", "2"), "locks", "abc");
        assertThat(redisTemplate.opsForValue().get("locks:1")).isNull();
        assertThat(redisTemplate.opsForValue().get("locks:2")).isNull();
    }

    @Test
    public void shouldNotReleaseWhenTokenDoesNotFullyMatch() {
        redisTemplate.opsForValue().set("locks:1", "def");
        redisTemplate.opsForValue().set("locks:2", "ghi");
        lock.release(Arrays.asList("1", "2"), "locks", "abc");
        assertThat(redisTemplate.opsForValue().get("locks:1")).isEqualTo("def");
        assertThat(redisTemplate.opsForValue().get("locks:2")).isEqualTo("ghi");
    }

    @Test
    public void shouldNotReleaseWhenTokenDoesNotPartiallyMatch() {
        redisTemplate.opsForValue().set("locks:1", "def");
        lock.release(Arrays.asList("1", "2"), "locks", "abc");
        assertThat(redisTemplate.opsForValue().get("locks:1")).isEqualTo("def");
        assertThat(redisTemplate.opsForValue().get("locks:2")).isNull();
    }

    @Test
    public void shouldRefresh() throws InterruptedException {
        final List<String> keys = Arrays.asList("1", "2");

        final String token = lock.acquire(keys, "locks", 1000);
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
        assertThat(redisTemplate.getExpire("locks:2", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
        Thread.sleep(500);
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(500, Offset.offset(100L));
        assertThat(redisTemplate.getExpire("locks:2", TimeUnit.MILLISECONDS)).isCloseTo(500, Offset.offset(100L));
        assertThat(lock.refresh(keys, "locks", token, 1000)).isTrue();
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
        assertThat(redisTemplate.getExpire("locks:2", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
    }

    @Test
    public void shouldNotRefreshBecauseOneKeyExpired() {
        final List<String> keys = Arrays.asList("1", "2");

        final String token = lock.acquire(keys, "locks", 1000);
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
        assertThat(redisTemplate.getExpire("locks:2", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));

        redisTemplate.delete("locks:2");

        assertThat(lock.refresh(keys, "locks", token, 1000)).isFalse();
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
        assertThat(redisTemplate.opsForValue().get("locks:2")).isNull();
    }

    @Test
    public void shouldNotRefreshBecauseTokenForOneKeyDoesNotMatch() {
        final List<String> keys = Arrays.asList("1", "2");

        final String token = lock.acquire(keys, "locks", 1000);
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
        assertThat(redisTemplate.getExpire("locks:2", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));

        redisTemplate.opsForValue().set("locks:1", "wrong-token");

        assertThat(lock.refresh(keys, "locks", token, 1000)).isFalse();
        assertThat(redisTemplate.getExpire("locks:1", TimeUnit.MILLISECONDS)).isEqualTo(-1L); // expiration was removed on manual update in this test
        assertThat(redisTemplate.getExpire("locks:2", TimeUnit.MILLISECONDS)).isCloseTo(1000, Offset.offset(100L));
        assertThat(redisTemplate.opsForValue().get("locks:1")).isEqualTo("wrong-token");
        assertThat(redisTemplate.opsForValue().get("locks:2")).isEqualTo(token);
    }

    @Test
    public void shouldExpire() throws InterruptedException {
        lock.acquire(Arrays.asList("1", "2"), "locks", 100);
        Thread.sleep(100);
        assertThat(redisTemplate.opsForValue().get("locks:1")).isNull();
        assertThat(redisTemplate.opsForValue().get("locks:2")).isNull();
    }

    @SpringBootApplication(scanBasePackageClasses = EmbeddedRedis.class)
    static class TestApplication {
    }
}