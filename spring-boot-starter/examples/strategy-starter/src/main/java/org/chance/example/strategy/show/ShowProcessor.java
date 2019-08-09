package org.chance.example.strategy.show;

import org.chance.strategy.autoconfigure.StrategyAnnotation;
import org.chance.strategy.autoconfigure.Strategyable;

/**
 * ShowProcessor
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/1
 */
@StrategyAnnotation(bizType = ShowProcessor.BIZ_TYPE)
public interface ShowProcessor extends Strategyable {

    String BIZ_TYPE = "ActPrizeShowProcessor";

    /**
     * @param content
     * @return
     */
    void process(String content);

}
