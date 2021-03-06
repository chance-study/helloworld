package org.chance.distributelock.jdbc.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.data.Offset;
import org.chance.distributelock.api.Lock;
import org.chance.distributelock.jdbc.service.SimpleJdbcLockSingleKeyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@DirtiesContext
@RunWith(SpringRunner.class)
@Sql(value = "/locks-table-create.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = "/locks-table-drop.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class SimpleJdbcLockTest implements InitializingBean {

    @Autowired
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") // false IntelliJ warning
    private JdbcTemplate jdbcTemplate;

    private Lock lock;

    @Override
    public void afterPropertiesSet() {
        // instead of writing a custom test configuration, we can just initialize it after autowiring mongoTemplate with a custom tokenSupplier
        lock = new SimpleJdbcLock(new SimpleJdbcLockSingleKeyService(jdbcTemplate), () -> "abc");
    }

    @Test
    public void shouldLock() {
        final long now = System.currentTimeMillis();
        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(token).isEqualTo("abc");

        final Map<String, Object> acquiredLockMap = jdbcTemplate.queryForObject("SELECT * FROM locks WHERE id = 1", new ColumnMapRowMapper());

        assertThat(acquiredLockMap).containsAllEntriesOf(values("1", "abc"));
        final Object expireAt = acquiredLockMap.get("expireAt");
        assertThat(((Date) expireAt).getTime()).isCloseTo(now + 1000, Offset.offset(100L));
    }

    @Test
    public void shouldNotLock() {
        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("locks")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(values("1", "def"));

        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        assertThat(token).isNull();

        final Map<String, Object> acquiredLockMap = jdbcTemplate.queryForObject("SELECT * FROM locks WHERE id = 1", new ColumnMapRowMapper());
        assertThat(acquiredLockMap).containsAllEntriesOf(values("1", "def"));
    }

    @Test
    public void shouldRelease() {
        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("locks")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(values("1", "abc"));

        final boolean released = lock.release(Collections.singletonList("1"), "locks", "abc");
        assertThat(released).isTrue();
        assertThat(jdbcTemplate.queryForList("SELECT * FROM locks")).isNullOrEmpty();
    }

    @Test
    public void shouldNotRelease() {
        new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("locks")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(values("1", "def"));

        lock.release(Collections.singletonList("1"), "locks", "abc");

        final Map<String, Object> acquiredLockMap = jdbcTemplate.queryForObject("SELECT * FROM locks WHERE id = 1", new ColumnMapRowMapper());
        assertThat(acquiredLockMap).containsAllEntriesOf(values("1", "def"));
    }

    @Test
    public void shouldRefresh() throws InterruptedException {
        LocalDateTime expectedExpiration = LocalDateTime.now().plus(1000, ChronoUnit.MILLIS);

        final String token = lock.acquire(Collections.singletonList("1"), "locks", 1000);
        Map<String, Object> acquiredLockMap = jdbcTemplate.queryForObject("SELECT * FROM locks WHERE id = 1", new ColumnMapRowMapper());
        assertThat((Timestamp) acquiredLockMap.get("expireAt")).isCloseTo(expectedExpiration.toString(), 100);
        Thread.sleep(500);
        acquiredLockMap = jdbcTemplate.queryForObject("SELECT * FROM locks WHERE id = 1", new ColumnMapRowMapper());
        assertThat((Timestamp) acquiredLockMap.get("expireAt")).isCloseTo(expectedExpiration.toString(), 100);
        expectedExpiration = LocalDateTime.now().plus(1000, ChronoUnit.MILLIS);
        assertThat(lock.refresh(Collections.singletonList("1"), "locks", token, 1000)).isTrue();
        acquiredLockMap = jdbcTemplate.queryForObject("SELECT * FROM locks WHERE id = 1", new ColumnMapRowMapper());
        assertThat((Timestamp) acquiredLockMap.get("expireAt")).isCloseTo(expectedExpiration.toString(), 100);
    }

    @Test
    public void shouldNotRefreshBecauseTokenDoesNotMatch() {
        final List<String> keys = Collections.singletonList("1");

        final String token = lock.acquire(keys, "locks", 1000);
        final Map<String, Object> acquiredLockMap = jdbcTemplate.queryForObject("SELECT * FROM locks WHERE id = 1", new ColumnMapRowMapper());
        assertThat(acquiredLockMap).containsAllEntriesOf(values("1", token));

        assertThat(lock.refresh(keys, "locks", "wrong-token", 1000)).isFalse();

        assertThat(jdbcTemplate.queryForList("SELECT * FROM locks")).hasSize(1);
    }

    @Test
    public void shouldNotRefreshBecauseKeyExpired() {
        final List<String> keys = Collections.singletonList("1");

        assertThat(lock.refresh(keys, "locks", "abc", 1000)).isFalse();

        assertThat(jdbcTemplate.queryForList("SELECT * FROM locks")).isNullOrEmpty();
    }

    private static Map<String, Object> values(final String key, final String token) {
        final Map<String, Object> values = new HashMap<>();
        values.put("lock_key", key);
        values.put("token", token);
        return values;
    }

    @SpringBootApplication
    static class TestApplication {
    }
}