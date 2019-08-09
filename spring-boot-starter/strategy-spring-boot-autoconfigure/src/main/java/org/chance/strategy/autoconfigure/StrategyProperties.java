package org.chance.strategy.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * StrategyProperties
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/7/30
 */

@ConfigurationProperties("strategy")
public class StrategyProperties {

    /**
     * 懒加载
     */
    private boolean lazy;

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }
}
