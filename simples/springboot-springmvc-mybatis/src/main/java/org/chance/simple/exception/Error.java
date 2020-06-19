package org.chance.simple.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Error
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2020/5/20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error<T> {

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示信息
     */
    private String msg;

    /**
     *
     */
    private T data;

}
