package org.chance.dubbo.consumer.controller;

import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GlobalExceptionHandler
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/3
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 当不配置异常处理的时候，默认会返回：
     * {
     *     "timestamp": "2018-11-09T01:47:56.985+0000",
     *     "status": 400,
     *     "error": "Bad Request",
     *     "errors": [
     *         {
     *             "codes": [
     *                 "Size.user.password",
     *                 "Size.password",
     *                 "Size.java.lang.String",
     *                 "Size"
     *             ],
     *             "arguments": [
     *                 {
     *                     "codes": [
     *                         "user.password",
     *                         "password"
     *                     ],
     *                     "arguments": null,
     *                     "defaultMessage": "password",
     *                     "code": "password"
     *                 },
     *                 20,
     *                 8
     *             ],
     *             "defaultMessage": "密码必须大于8位并且小于20位",
     *             "objectName": "user",
     *             "field": "password",
     *             "rejectedValue": "huanfe",
     *             "bindingFailure": false,
     *             "code": "Size"
     *         }
     *     ]
     *     # 省略trace信息
     * }
     *
     * 默认情况下，SpringBoot配置了默认异常处理器 DefaultHandlerExceptionResolver，而该处理器仅仅是将异常信息打印出来，显然，我们并不需要返回如此多的信息，只需要将对应属性中的message信息给调用者即可，解决的方法有两种：
     *
     * 1. 在需要验证的方法中加入BindingResult参数，SpringBoot会自动将异常错误信息绑定到该参数上，然后处理对应的逻辑 （优先级高） 一个校验类对应一个校验结果（@Validated Foo foo, BindingResult fooBindingResult ，@Validated Bar bar, BindingResult barBindingResult）
     *
     * 2. 由于在验证失败的时候，会抛出异常，所以可以使用全局异常处理器来捕获该异常，然后进行统一处理即可，具体的异常类型是MethodArgumentNotValidException，具体实现如下所示：
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<?> validationErrorHandler(MethodArgumentNotValidException ex) {
        // 同样是获取BindingResult对象，然后获取其中的错误信息
        // 如果前面开启了fail_fast，事实上这里只会有一个信息
        //如果没有，则可能又多个
        List<String> errorInformation = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return new Result<>(400, errorInformation.toString(), null);
    }

    /**
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result<?> validationErrorHandler(ConstraintViolationException ex) {
        List<String> errorInformation = ex.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
        return new Result<>(400, errorInformation.toString(), null);
    }
}