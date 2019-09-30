package org.chance.distributelock.mongo.impl;

import org.assertj.core.data.TemporalUnitWithinOffset;
import org.chance.distributelock.api.Lock;
import org.chance.distributelock.mongo.model.LockDocument;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@DirtiesContext
@RunWith(SpringRunner.class)
public class SimpleMongoLockTest implements InitializingBean {

    @Autowired
    private MongoTemplate mongoTemplate;

    private Lock lock;

    @Override
    public void afterPropertiesSet() {
        // instead of writing a custom test configuration, we can just initialize it after autowiring mongoTemplate with a custom tokenSupplier
        lock = new SimpleMongoLock(mongoTemplate, () -> "abc");
    }

    @Before
    public void cleanMongoCollection() {
        mongoTemplate.dropCollection("locks");
    }

    @Test
    public void shouldLock() {
        final LocalDateTime expectedExpiration = LocalDateTime.now().plus(1000, ChronoUnit.MILLIS);

        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(token).isEqualTo("abc");
        final LockDocument document = mongoTemplate.findById("1", LockDocument.class, "locks");
        assertThat(document.getToken()).isEqualTo("abc");
        assertThat(document.getExpireAt()).isCloseTo(expectedExpiration, new TemporalUnitWithinOffset(100, ChronoUnit.MILLIS));
    }

    @Test
    public void shouldNotLock() {
        mongoTemplate.insert(new LockDocument("1", LocalDateTime.now().plusMinutes(1), "def"), "locks");
        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(token).isNull();
        assertThat(mongoTemplate.findById("1", LockDocument.class, "locks").getToken()).isEqualTo("def");
    }

    @Test
    public void shouldRelease() {
        mongoTemplate.insert(new LockDocument("1", LocalDateTime.now().plusMinutes(1), "abc"), "locks");
        final boolean released = lock.release(Collections.singletonList("1"), "locks", "abc");
        assertThat(released).isTrue();
        assertThat(mongoTemplate.findById("1", LockDocument.class, "locks")).isNull();
    }

    @Test
    public void shouldNotRelease() {
        mongoTemplate.insert(new LockDocument("1", LocalDateTime.now().plusMinutes(1), "def"), "locks");
        final boolean released = lock.release(Collections.singletonList("1"), "locks", "abc");
        assertThat(released).isFalse();
        assertThat(mongoTemplate.findById("1", LockDocument.class, "locks").getToken()).isEqualTo("def");
    }

    @Test
    public void shouldRefresh() throws InterruptedException {
        LocalDateTime expectedExpiration = LocalDateTime.now().plus(1000, ChronoUnit.MILLIS);

        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(mongoTemplate.findById("1", LockDocument.class, "locks").getExpireAt()).isCloseTo(expectedExpiration, new TemporalUnitWithinOffset(100, ChronoUnit.MILLIS));
        Thread.sleep(500);
        assertThat(mongoTemplate.findById("1", LockDocument.class, "locks").getExpireAt()).isCloseTo(expectedExpiration, new TemporalUnitWithinOffset(100, ChronoUnit.MILLIS));
        expectedExpiration = LocalDateTime.now().plus(1000, ChronoUnit.MILLIS);
        assertThat(lock.refresh(Collections.singletonList("1"), "locks", token, 1000)).isTrue();
        assertThat(mongoTemplate.findById("1", LockDocument.class, "locks").getExpireAt()).isCloseTo(expectedExpiration, new TemporalUnitWithinOffset(100, ChronoUnit.MILLIS));
    }

    @Test
    public void shouldNotRefreshBecauseTokenDoesNotMatch() {
        LocalDateTime expectedExpiration = LocalDateTime.now().plus(1000, ChronoUnit.MILLIS);

        lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(mongoTemplate.findById("1", LockDocument.class, "locks").getExpireAt()).isCloseTo(expectedExpiration, new TemporalUnitWithinOffset(100, ChronoUnit.MILLIS));
        assertThat(lock.refresh(Collections.singletonList("1"), "locks", "wrong-token", 1000)).isFalse();
        assertThat(mongoTemplate.findById("1", LockDocument.class, "locks").getExpireAt()).isCloseTo(expectedExpiration, new TemporalUnitWithinOffset(100, ChronoUnit.MILLIS));
    }

    @Test
    public void shouldNotRefreshBecauseKeyExpired() {
        assertThat(lock.refresh(Collections.singletonList("1"), "locks", "abc", 1000)).isFalse();
        assertThat(mongoTemplate.findAll(LockDocument.class)).isNullOrEmpty();
    }

    @SpringBootApplication
    static class TestApplication {
    }
}