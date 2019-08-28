package org.chance.strategy.autoconfigure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略生产的工厂
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2018/6/8
 */

public class StrategyFactory {

    private static final Logger log = LoggerFactory
            .getLogger(StrategyFactory.class);

    /**
     * 计数器
     */
    private static int count = 0;

    /**
     * 单利模式
     */
    private StrategyFactory() {

        /**
         * 防止通过反射破坏单例
         */
        synchronized (StrategyFactory.class) {
            if (count > 0) {
                throw new RuntimeException("创建了两个实例");
            }
            count++;
        }
    }

    /**
     * 实例懒加载
     */
    private static class SingletonHolder {

        private static final StrategyFactory INSTANCE = new StrategyFactory();

        // 解决并发初始化的问题
        static {
            log.info("懒加载实例的时候，进行初始化。。。");
            INSTANCE.init();
        }

    }

    /**
     * 获取工厂的对象
     *
     * @return
     */
    public static final StrategyFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 存储对应的枚举和策略处理类的关系
     */
    private final Map<String, Strategyable> strategys = new HashMap<>();

    private void putStrategy(String key, Strategyable strategy) {
        if (key != null && strategy != null) {
            log.info("策略生产的工厂 建立一条策略对应关系 {} ---> {}", key, strategy);
            try {
                strategys.put(key, strategy);
            } catch (Exception e) {
                log.error("策略生产的工厂 建立一条策略对应关系 出错 类型不匹配", e);
            }

        }
    }

    /**
     * 根据类型获取对应的策略工厂
     */
    public <I extends Strategyable> I createStrategy(Enum e, String bizType) {
        // 默认不返回默认策略实现
        return createStrategy(e, bizType, false);
    }

    /**
     * 根据类型获取对应的策略工厂
     */
    public <I extends Strategyable> I createStrategy(Enum e, String bizType, boolean defaultSwitch) {

        if (!defaultSwitch && e == null) {
            return null;
        }

        Strategyable strategyable = strategys.get(buildKey(e, bizType));

        if (strategyable == null && defaultSwitch) {
            // 当开启默认开关的时候，当没有取到对应枚举对应的策略的时候，去默认策略
            return (I) strategys.get(buildKey(null, bizType));
        }

        return (I) strategys.get(buildKey(e, bizType));
    }

    private String buildKey(Enum e, String bizType) {
        StringBuilder key = new StringBuilder();
        key.append(bizType == null ? "" : bizType)
                .append(":")
                .append(e != null ? e.getClass().getName() : "default")
                .append(":")
                .append(e != null ? e.name() : "default");
        return key.toString();
    }

    /**
     * 初始化的方法
     */
    private void init() {

        ApplicationContext ctx = ApplicationContextHolder.getApplicationContext();

        if (ctx == null) {
            return;
        }

        Map<String, Object> beans = ctx.getBeansWithAnnotation(StrategyAnnotation.class);

        if (beans != null && beans.size() > 0) {
            beans.forEach((k, v) -> {
                if (v instanceof Strategyable) {
                    StrategyAnnotation av =
                            ctx.findAnnotationOnBean(k, StrategyAnnotation.class);
                    Enum e = ((Strategyable) v).bindEnumKey();
                    if (av != null) {
                        putStrategy(buildKey(e, av.bizType()), (Strategyable) v);
                    }
                }

            });

        }

    }

}
