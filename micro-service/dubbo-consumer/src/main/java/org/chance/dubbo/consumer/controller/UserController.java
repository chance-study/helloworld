package org.chance.dubbo.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * 检验Controller的入参是否符合规范时，使用@Validated或者@Valid在基本验证功能上没有太多区别。但是在分组、注解地方、嵌套验证等功能上两个有所不同：
 * 1. 分组
 * @Validated：提供了一个分组功能，可以在入参验证时，根据不同的分组采用不同的验证机制，这个网上也有资料，不详述。@Valid：作为标准JSR-303规范，还没有吸收分组的功能。
 * 2. 注解地方
 * @Validated：可以用在类型、方法和方法参数上。但是不能用在成员属性（字段）上
 * @Valid：可以用在方法、构造函数、方法参数和成员属性（字段）上
 * 两者是否能用于成员属性（字段）上直接影响能否提供嵌套验证的功能。
 * 3. 嵌套验证
 * @Validated：用在方法入参上无法单独提供嵌套验证功能。不能用在成员属性（字段）上，也无法提示框架进行嵌套验证。能配合嵌套验证注解@Valid进行嵌套验证。
 * @Valid：用在方法入参上无法单独提供嵌套验证功能。能够用在成员属性（字段）上，提示验证框架进行嵌套验证。能配合嵌套验证注解@Valid进行嵌套验证。
 * 4.两者出发的异常不一样
 *
 *
 *
 * 自动配置类：org.springframework.boot.autoconfigure.validation.ValidationAutoConfiguration
 *
 * 常用注解：
 * @Null，标注的属性值必须为空
 * @NotNull，标注的属性值不能为空
 * @AssertTrue，标注的属性值必须为true
 * @AssertFalse，标注的属性值必须为false
 * @Min，标注的属性值不能小于min中指定的值
 * @Max，标注的属性值不能大于max中指定的值
 * @DecimalMin，小数值，同上
 * @DecimalMax，小数值，同上
 * @Negative，负数
 * @NegativeOrZero，0或者负数
 * @Positive，整数
 * @PositiveOrZero，0或者整数
 * @Size，指定字符串长度，注意是长度，有两个值，min以及max，用于指定最小以及最大长度
 * @Digits，内容必须是数字
 * @Past，时间必须是过去的时间
 * @PastOrPresent，过去或者现在的时间
 * @Future，将来的时间
 * @FutureOrPresent，将来或者现在的时间
 * @Pattern，用于指定一个正则表达式
 * @NotEmpty，字符串内容非空
 * @NotBlank，字符串内容非空且长度大于0
 * @Email，邮箱
 * @Range，用于指定数字，注意是数字的范围，有两个值，min以及max
 *
 * UserController
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/3
 */
@RestController
@RequestMapping("/users")
@Validated
@Slf4j
public class UserController {

    @Autowired
    private Validator validator;

    /**
     *
     * @param name
     * @return
     */
    @GetMapping("/{name}")
//    @GetMapping("/{name:[a-zA-Z]+")
    public User getUserByName(
            @NotNull
            @Size(min = 1, max = 4, message = "用户名格式有误")
            @PathVariable String name) {

        /**
         * 要想校验失效，需要在类的上方使用@Validated进行标注，注意是标注在类上方
         * 在方法前面加@Validated、@Valid 都是没有用的
         *
         * 校验不通过会报 javax.validation.ConstraintViolationException 这里异常
         *
         */

        User user = new User();
        user.setName(name);
        return user;
    }

    @GetMapping
    public User getUserByNameParam(
            @NotNull
            @Size(min = 1, max = 4, message = "用户名格式有误")
            @RequestParam("name") String name) {
        User user = new User();
        user.setName(name);
        return user;
    }

    @RequestMapping("/bar")
    public @NotBlank String bar(@Min(18) Integer age) {
        log.info("age : {} ", age);
        return "";
    }

    /**
     *
     * @Valid 可以校验@RequestBody标注的参数的验证及错误处理，然而，并不能处理@PathVariable以及@RequestParam标注的入参(不生效)
     *
     * 校验不通过会报 MethodArgumentNotValidException 这个异常
     *
     * @param user
     * @return
     */
    @PostMapping
    public User addUser(@Valid @RequestBody User user) {

        /**
         * @Valid注解，通过该注解能够使得验证生效，如果去除的话，可以看到验证逻辑并没有生效。
         */
        // 仅测试验证过程，省略其他的逻辑
        return user;
    }

    /**
     *
     * @Valid 可以校验@RequestBody标注的参数的验证及错误处理，然而，并不能处理@PathVariable以及@RequestParam标注的入参(不生效)
     *
     * 校验不通过会报 MethodArgumentNotValidException 这个异常
     *
     * @param user
     * @return
     */
    @PostMapping("/add")
    public User addUser1(@RequestBody User user) {

        // 程序中手动校验 基于spring的bean
        // spring 的实现 LocalValidatorFactoryBean
//        Set<ConstraintViolation<User>> set = validator.validate(user);
// 此处校验接口最终的实现类便是LocalValidatorFactoryBean。
//        for (ConstraintViolation<User> constraintViolation : set) {
//            log.info(constraintViolation.getMessage());
//        }

        // 程序中手动校验 使用Hibernate Validation提供Validator
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        Validator validator = vf.getValidator();
        Set<ConstraintViolation<User>> set = validator.validate(user);
        for (ConstraintViolation<User> constraintViolation : set) {
            log.info(constraintViolation.getMessage());
        }

        return user;
    }

    /**
     *
     * @param user
     * @return
     */
    @PostMapping("/add2")
    public User addUser2(@Valid @RequestBody User user) {

        /**
         * @Valid注解，通过该注解能够使得验证生效，如果去除的话，可以看到验证逻辑并没有生效。
         */
        // 仅测试验证过程，省略其他的逻辑
        return user;
    }

}
