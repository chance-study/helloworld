package org.chance.dubbo.consumer.controller;

import lombok.Data;

/**
 * 当没有getter/setter方法的时候报：
 * org.springframework.http.converter.HttpMessageNotWritableException: No converter found for return value of type: class org.chance.dubbo.consumer.controller.Result
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/3
 */
@Data
public class Result<T> {

    private int code;
    private String message;
    private T body;

    public Result(int code, String message, T body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public Result(int code, String message) {
        this(code, message, null);
    }

    public Result(String message) {
        this(200, message, null);
    }

}
