package org.chance.dubbo.provider.listener;

import lombok.extern.slf4j.Slf4j;
import org.chance.dubbo.provider.service.EmbeddedZooKeeper;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * ApplicationStartListener
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/9
 */

@Component
@Slf4j
public class ApplicationStartListener {

    /**
     * 内嵌的zk服务器
     */
    private EmbeddedZooKeeper embeddedZooKeeper;


    /**
     * spring上下文启动完成触发,既ConfigurableApplicationContext的start方法。奇怪的是spring自己启动完成后触发的不是这个事件，而是RefreshedEvent。
     *
     * @param contextRefreshedEvent
     */
    @EventListener(classes = {ContextRefreshedEvent.class})
    public synchronized void contextStartedListener(ContextRefreshedEvent contextRefreshedEvent) {

        log.info("[spring listener] ContextRefreshedEvent start");

        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            // 防止多次执行
            (embeddedZooKeeper = new EmbeddedZooKeeper(2181, false))
                    .start();

        }

        log.info("[spring listener] ContextRefreshedEvent end");

    }

    @EventListener(classes = {ContextStoppedEvent.class})
    public synchronized void contextStartedListener(ContextStoppedEvent contextStoppedEvent) {

        log.info("[spring listener] ContextStoppedEvent start");

        if (embeddedZooKeeper != null) {
            embeddedZooKeeper.stop();
        }

        log.info("[spring listener] ContextStoppedEvent end");

    }
}
