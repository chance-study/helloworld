package org.chance.example.strategy.show;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 用户奖品显示个性化处理器
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019-07-11 16:49:03
 */

@Slf4j
@Service("showOfInvitationProcessor")
public class ShowOfOldProcessor extends AbstractShowProcessor {

    @Override
    public Enum bindEnumKey() {
        // 默认实现，绑定null枚举就可以了
        return ShowProcessorEnum.OLD;
    }

    @Override
    protected void customize() {
        super.customize();
        log.info("old extra customize");
    }
}
