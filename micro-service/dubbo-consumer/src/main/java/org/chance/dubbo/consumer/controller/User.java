package org.chance.dubbo.consumer.controller;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/3
 */
@Data
public class User {
    
    @NotBlank(message = "用户名不能为空")
    private String name;

    @Max(value = 120, message = "年龄不能超过120岁")
    private int age;

    @NotNull
    @Size(min = 8, max = 20, message = "密码必须大于8位并且小于20位")
    private String password;

    @Email(message = "请输入符合格式的邮箱")
    private String email;

    /**
     *  嵌套验证必须用@Valid
     */
    @Valid
    @NotNull(message = "prop不能为null")
    private Prop prop;

}
