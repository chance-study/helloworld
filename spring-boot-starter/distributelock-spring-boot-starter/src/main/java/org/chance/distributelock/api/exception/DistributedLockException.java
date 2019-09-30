package org.chance.distributelock.api.exception;

/**
 * DistributedLockException
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
public class DistributedLockException extends RuntimeException {

    public DistributedLockException(final String message) {
        super(message);
    }

    public DistributedLockException(final String msg, final Throwable e) {
        super(msg, e);
    }

}
