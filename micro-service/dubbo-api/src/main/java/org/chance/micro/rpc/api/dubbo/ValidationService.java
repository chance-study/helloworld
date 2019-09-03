package org.chance.micro.rpc.api.dubbo;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * ValidationService
 *
 * dubbo的参数校验是通过 ValidationFilter 过滤器实现的
 * 校验失败的时候触发 ConstraintViolationException  异常
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/2
 */
public interface ValidationService {

    // 验证参数不为空
    void save(@NotNull ValidationParameter parameter); // 验证参数不为空

    void delete(@Min(value = 1, message = "id不能小于1") int id); // 直接对基本类型参数验证

    //分组验证示例
    // 缺省可按服务接口区分验证场景，如：@NotNull(groups = ValidationService.class)
//    @interface Save{} // 与方法同名接口，首字母大写，用于区分验证场景，如：@NotNull(groups = ValidationService.Save.class)，可选
//    void save(ValidationParameter parameter);
//    void update(ValidationParameter parameter);

    //关联验证示例
//    @GroupSequence(Update.class) // 同时验证Update组规则
//    @interface Save{}
//    void save(ValidationParameter parameter);
//
//    @interface Update{}
//    void update(ValidationParameter parameter);
}
