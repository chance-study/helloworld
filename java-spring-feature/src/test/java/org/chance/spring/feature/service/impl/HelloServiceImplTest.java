package org.chance.spring.feature.service.impl;

import org.chance.spring.feature.JavaSpringFeatureApplication;
import org.chance.spring.feature.aop.LogAspect;
import org.chance.spring.feature.aop.config.AopConfig;
import org.chance.spring.feature.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ContextConfiguration 默认不写 可以根据测试的类名，去找到与之对应的配置文件。
 * HelloServiceImplTest-context.xml
 */
@RunWith(SpringRunner.class)
//@SpringBootTest(classes = HelloServiceImplTest.Application.class)
@SpringBootTest(classes = JavaSpringFeatureApplication.class)
@ActiveProfiles("aop")
//@ContextConfiguration(classes = {
//        HelloServiceImpl.class,
//        LogAspect.class,
//        AopConfig.class
//})
public class HelloServiceImplTest {

    @Autowired
    HelloService helloService;

    @Test
    public void hello() {
        helloService.hello();
    }

    @SpringBootApplication(exclude = {AopAutoConfiguration.class})
    static class Application {
        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }

}