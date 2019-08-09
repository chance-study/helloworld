package org.chance.strategy.autoconfigure;

import org.junit.Test;

import java.lang.reflect.Constructor;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StrategyFactoryTest {

    @Test
    public void testSingleton() throws NoSuchMethodException {

        /**
         * 先获取实例
         */
        StrategyFactory strategyFactory = StrategyFactory.getInstance();

        // 通过反射破坏单利
        Constructor<StrategyFactory> constructor = StrategyFactory.class.getDeclaredConstructor();
        constructor.setAccessible(true);

        /**
         * 通过反射创建对象会报错
         */
        assertThatThrownBy(() -> constructor.newInstance());

    }

}