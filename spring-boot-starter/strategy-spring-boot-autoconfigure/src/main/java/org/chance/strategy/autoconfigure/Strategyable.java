package org.chance.strategy.autoconfigure;

/**
 * 可实现策略的接口
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2018/6/8
 */
public interface Strategyable {

    /**
     * 该策略类绑定的枚举 Key，用于作为唯一标识
     *
     * @return
     */
    Enum bindEnumKey();

}
