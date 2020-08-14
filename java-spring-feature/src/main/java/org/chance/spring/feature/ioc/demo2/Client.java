package org.chance.spring.feature.ioc.demo2;

import org.chance.spring.feature.ioc.demo1.Parent;
import org.chance.spring.feature.ioc.demo1.RootConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-24 15:04:14
 */
public class Client {

    public static void main(String[] args) {
        // 备注：此处只能用RootConfig,而不能AppConfig(启动报错)，因为它要web容器支持，比如Tomcat
        ApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();

//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.chance.spring.feature.ioc");

        System.out.println(applicationContext.getBean(Parent.class)); //org.chance.spring.feature.ioc.demo1.Parent@7f010382

    }

}
