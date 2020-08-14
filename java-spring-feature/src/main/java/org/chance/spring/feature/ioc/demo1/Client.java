package org.chance.spring.feature.ioc.demo1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-24 15:04:14
 */
public class Client {

    public static void main(String[] args) {
        // 备注：此处只能用RootConfig,而不能AppConfig(启动报错)，因为它要web容器支持，比如Tomcat
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfig.class);
//        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("org.chance.spring.feature.ioc");

        System.out.println(applicationContext.getBean(Parent.class)); //org.chance.spring.feature.ioc.demo1.Parent@7f010382

    }

}
