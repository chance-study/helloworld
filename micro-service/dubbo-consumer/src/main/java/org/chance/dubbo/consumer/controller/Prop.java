package org.chance.dubbo.consumer.controller;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Prop
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/3
 */
@Data
public class Prop {

    @NotNull(message = "pid不能为空")
    @Min(value = 1, message = "pid必须为正整数")
    private Long pid;

    @NotBlank(message = "pidName不能为空")
    private String pidName;

}
