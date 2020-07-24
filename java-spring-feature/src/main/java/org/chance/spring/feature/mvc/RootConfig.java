package org.chance.spring.feature.mvc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-24 14:44:52
 */

// 备注：此处@ControllerAdvice、RestControllerAdvice 这个注解不要忘了，属于Controller层处理全局异常的，应该交给web去扫描
@ComponentScan(value = "org.chance.spring.feature.ioc", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, ControllerAdvice.class, RestControllerAdvice.class})
})
@Configuration //最好标注上，本人亲测若不标准，可能扫描不生效
public class RootConfig {

}
