package org.chance.strategy.autoconfigure;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 策略绑定枚举的注解
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2018/6/8
 */
@Target(ElementType.TYPE)//表示只能给类添加该注解
@Retention(RetentionPolicy.RUNTIME)//这个必须要将注解保留在运行时
@Inherited
public @interface StrategyAnnotation {

    /**
     * 绑定的业务类型
     *
     * @return
     */
    String bizType();

}
