package org.chance.spring.feature.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.chance.spring.feature.service.HelloService;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * HelloServiceImpl
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2020/6/29
 */
@Slf4j
@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private BeanFactory beanFactory;

    @Override
    public Object hello() {
        log.info("this is my method~~");

        // 此处特意把this输出来对比，请务必注意差别
        log.info("{}", this.getClass()); //class com.fsx.service.HelloServiceImpl
        log.info("{}", beanFactory.getBean(HelloService.class).getClass()); //class com.sun.proxy.$Proxy32   是JDK的动态代理了
        return "service hello";
    }

}
