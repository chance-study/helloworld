package org.chance.spring.feature.ioc;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-24 14:45:14
 */
// 此处记得排除掉@Controller和@ControllerAdvice、@RestControllerAdvice
@ComponentScan(value = "org.chance.spring.feature.ioc", useDefaultFilters = false,
        includeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, ControllerAdvice.class, RestControllerAdvice.class})}
)
@Configuration //最好标注上，本人亲测若不标准，可能扫描不生效
public class AppConfig {

}
