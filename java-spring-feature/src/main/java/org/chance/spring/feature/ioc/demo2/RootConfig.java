package org.chance.spring.feature.ioc.demo2;

import org.chance.spring.feature.ioc.demo1.AppConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-24 14:44:52
 */
// 因为我们只需要了解Bean的加载，所以只需要启动一个容器就行，并不需要web环境，因此本文用一个相对简单的环境，来进行讲解，如下：
@ComponentScan(value = "org.chance.spring.feature.ioc.demo2", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
        //排除掉web容器的配置文件，否则会重复扫描
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {AppConfig.class}),
})
@Configuration //最好标注上，本人亲测若不标准，可能扫描不生效 ClassPathBeanDefinitionScanner#scan扫描不到
public class RootConfig {

    @Bean("person")
    public Person person() {
        return new Person("p1");
    }

}
