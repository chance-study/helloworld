package org.chance.distributelock.core.advice;

import org.chance.distributelock.api.Lock;

@FunctionalInterface
public interface LockTypeResolver {

    /**
     * Get a {@link Lock} for the given {@code type}.
     *
     * @param type type of the lock
     * @return lock of the given {@code type}
     */
    Lock get(Class<? extends Lock> type);

}
