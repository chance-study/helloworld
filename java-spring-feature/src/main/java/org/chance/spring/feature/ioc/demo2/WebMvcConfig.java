package org.chance.spring.feature.ioc.demo2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-08-05 17:05:20
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean("person")
    public Person person() {
        Person person = new Person("child application");
        return person;
    }
}
