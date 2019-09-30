package org.chance.distributelock.mongo.impl;

import lombok.extern.slf4j.Slf4j;
import org.chance.distributelock.api.Lock;


import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import org.chance.distributelock.mongo.model.LockDocument;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Slf4j
public class SimpleMongoLock implements Lock {

    private final MongoTemplate mongoTemplate;
    private final Supplier<String> tokenSupplier;

    public SimpleMongoLock(final MongoTemplate mongoTemplate) {
        this(mongoTemplate, () -> UUID.randomUUID().toString());
    }

    public SimpleMongoLock(MongoTemplate mongoTemplate, Supplier<String> tokenSupplier) {
        this.mongoTemplate = mongoTemplate;
        this.tokenSupplier = tokenSupplier;
    }

    @Override
    public String acquire(final List<String> keys, final String storeId, final long expiration) {
        Assert.isTrue(keys.size() == 1, "Cannot acquire lock for multiple keys with this lock: " + keys);

        final String key = keys.get(0);
        final String token = tokenSupplier.get();

        if (StringUtils.isEmpty(token)) {
            throw new IllegalStateException("Cannot lock with empty token");
        }

        final Query query = Query.query(Criteria.where("_id").is(key));
        final Update update = new Update()
                .setOnInsert("_id", key)
                .setOnInsert("expireAt", LocalDateTime.now().plus(expiration, ChronoUnit.MILLIS))
                .setOnInsert("token", token);
        final FindAndModifyOptions options = new FindAndModifyOptions().upsert(true).returnNew(true);

        final LockDocument doc = mongoTemplate.findAndModify(query, update, options, LockDocument.class, storeId);

        final boolean locked = doc.getToken().equals(token);
        log.debug("Tried to acquire lock for key {} with token {} in store {}. Locked: {}", key, token, storeId, locked);
        return locked ? token : null;
    }

    @Override
    public boolean release(final List<String> keys, final String storeId, final String token) {
        Assert.isTrue(keys.size() == 1, "Cannot release lock for multiple keys with this lock: " + keys);

        final String key = keys.get(0);

        final DeleteResult deleted = mongoTemplate.remove(Query.query(Criteria.where("_id").is(key).and("token").is(token)), storeId);

        final boolean released = deleted.getDeletedCount() == 1;
        if (released) {
            log.debug("Remove query successfully affected 1 record for key {} with token {} in store {}", key, token, storeId);
        } else if (deleted.getDeletedCount() > 0) {
            log.error("Unexpected result from release for key {} with token {} in store {}, released {}", key, token, storeId, deleted);
        } else {
            log.error("Remove query did not affect any records for key {} with token {} in store {}", key, token, storeId);
        }

        return released;
    }

    @Override
    public boolean refresh(final List<String> keys, final String storeId, final String token, final long expiration) {
        Assert.isTrue(keys.size() == 1, "Cannot release lock for multiple keys with this lock: " + keys);

        final String key = keys.get(0);

        final UpdateResult updated = mongoTemplate.updateFirst(Query.query(Criteria.where("_id").is(key).and("token").is(token)),
                Update.update("expireAt", LocalDateTime.now().plus(expiration, ChronoUnit.MILLIS)),
                storeId);

        final boolean refreshed = updated.getModifiedCount() == 1;
        if (refreshed) {
            log.debug("Refresh query successfully affected 1 record for key {} with token {} in store {}", key, token, storeId);
        } else if (updated.getModifiedCount() > 0) {
            log.error("Unexpected result from refresh for key {} with token {} in store {}, released {}", key, token, storeId, updated);
        } else {
            log.warn("Refresh query did not affect any records for key {} with token {} in store {}. This is possible when refresh interval fires for the final time after the lock has been released",
                    key, token, storeId);
        }

        return refreshed;
    }
}
