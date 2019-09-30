package org.chance.distributelock.core.exception;

import org.chance.distributelock.api.exception.DistributedLockException;

/**
 * EvaluationConvertException
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
public class EvaluationConvertException extends DistributedLockException {

    public EvaluationConvertException(final String msg) {
        super(msg);
    }

}
