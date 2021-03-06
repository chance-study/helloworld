package org.chance.distributelock.jdbc.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
public class SimpleJdbcLockSingleKeyService implements JdbcLockSingleKeyService {

    private static final Logger log = LoggerFactory
        .getLogger(SimpleJdbcLockSingleKeyService.class);

    public static final String ACQUIRE_FORMATTED_QUERY = "INSERT INTO %s (lock_key, token, expireAt) VALUES (?, ?, ?);";
    public static final String RELEASE_FORMATTED_QUERY = "DELETE FROM %s WHERE lock_key = ? AND token = ?;";
    public static final String DELETE_EXPIRED_FORMATTED_QUERY = "DELETE FROM %s WHERE expireAt < ?;";
    public static final String REFRESH_FORMATTED_QUERY = "UPDATE %s SET expireAt = ? WHERE lock_key = ? AND token = ?;";

    private final JdbcTemplate jdbcTemplate;

    public SimpleJdbcLockSingleKeyService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String acquire(final String key, final String storeId, final String token, final long expiration) {
        final Date now = new Date();

        final int expired = jdbcTemplate.update(String.format(DELETE_EXPIRED_FORMATTED_QUERY, storeId), now);
        log.debug("Expired {} locks", expired);

        try {
            final Date expireAt = new Date(now.getTime() + expiration);
            final int created = jdbcTemplate.update(String.format(ACQUIRE_FORMATTED_QUERY, storeId), key, token, expireAt);
            return created == 1 ? token : null;
        } catch (final DuplicateKeyException e) {
            return null;
        }
    }

    @Override
    public boolean release(final String key, final String storeId, final String token) {
        final int deleted = jdbcTemplate.update(String.format(RELEASE_FORMATTED_QUERY, storeId), key, token);

        final boolean released = deleted == 1;
        if (released) {
            log.debug("Release query successfully affected 1 record for key {} with token {} in store {}", key, token, storeId);
        } else if (deleted > 0) {
            log.error("Unexpected result from release for key {} with token {} in store {}, released {}", key, token, storeId, deleted);
        } else {
            log.error("Release query did not affect any records for key {} with token {} in store {}", key, token, storeId);
        }

        return released;
    }

    @Override
    public boolean refresh(final String key, final String storeId, final String token, final long expiration) {
        final Date now = new Date();
        final Date expireAt = new Date(now.getTime() + expiration);

        final int updated = jdbcTemplate.update(String.format(REFRESH_FORMATTED_QUERY, storeId), expireAt, key, token);
        final boolean refreshed = updated == 1;
        if (refreshed) {
            log.debug("Refresh query successfully affected 1 record for key {} with token {} in store {}", key, token, storeId);
        } else if (updated > 0) {
            log.error("Unexpected result from refresh for key {} with token {} in store {}, refreshed {}", key, token, storeId, updated);
        } else {
            log.error("Refresh query did not affect any records for key {} with token {} in store {}", key, token, storeId);
        }

        return refreshed;
    }
}
