package org.chance.simple.exception;

/**
 * BizException
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2020/5/21
 */
public class BizException extends RuntimeException {

    /**
     * 定义业务异常返回的httpStatus
     */
    public static final int httpStatus = 600;

    public BizException() {
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
