package org.chance.strategy.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * ApplicationContextHolder
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/7/30
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {

    /**
     *
     */
    private static final Logger log = LoggerFactory
            .getLogger(ApplicationContextHolder.class);

    /**
     *
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        log.info("ApplicationContextHolder 设置 applicationContext");

        ApplicationContextHolder.applicationContext = applicationContext;

    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

}
