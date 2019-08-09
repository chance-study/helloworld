package org.chance.example.strategy.show;

import lombok.extern.slf4j.Slf4j;

/**
 * 用户奖品显示个性化处理器
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019-07-11 16:49:03
 */
@Slf4j
public abstract class AbstractShowProcessor implements ShowProcessor {

    /**
     * 默认的方法，可以被子类继承，实现不同的逻辑
     *
     * @return
     */
    protected void customize() {
        log.info("default customize");
    }

    @Override
    public void process(String content) {
        customize();
    }

}
