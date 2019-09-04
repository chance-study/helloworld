package org.chance.micro.rpc.api.exception;

/**
 * BizException
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/4
 */
public class BizException extends Exception {

    private static final long serialVersionUID = 7024056254695413813L;

    protected String code;

    // message 延用父类的message
//    protected String msg;

    protected Object data;

    public BizException(String code, Object data) {
        this.code = code;
        this.data = data;
    }

    public BizException(String message, String code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    public BizException(String message, Throwable cause, String code, Object data) {
        super(message, cause);
        this.code = code;
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 如果担心性能问题，在必要时，可以通过 override 掉异常类的 fillInStackTrace() 方法为空方法，使其不拷贝栈信息。
     * @return
     */
    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
