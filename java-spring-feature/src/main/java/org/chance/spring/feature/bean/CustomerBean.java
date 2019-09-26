package org.chance.spring.feature.bean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Customer
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/24
 */
@Slf4j
@Component
public class CustomerBean implements InitializingBean, DisposableBean, ApplicationContextAware, EnvironmentAware, BeanNameAware, ApplicationEventPublisherAware {

    /**
     * 对于 Bean 实现了DisposableBean，它将运行 destroy()在 Spring 容器释放该 bean 之后。
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {
        log.info("DisposableBean#destroy");
    }

    /**
     *
     * 对于Bean实现 InitializingBean，
     * 它将运行 afterPropertiesSet()在所有的 bean 属性被设置之后。
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("InitializingBean#afterPropertiesSet");
    }

    /**
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("ApplicationContextAware#setApplicationContext");
    }


    @Override
    public void setBeanName(String s) {
        log.info("BeanNameAware#setBeanName");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        log.info("ApplicationEventPublisherAware#setApplicationEventPublisher");
    }

    @Override
    public void setEnvironment(Environment environment) {
        log.info("EnvironmentAware#setEnvironment");
    }
}
