package org.chance.micro.rpc.api.dubbo;

import org.chance.micro.rpc.api.exception.BizException;

/**
 * 定义接口的时候要提供默认实现，防止引用未覆盖的时候出错
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/1
 */
public interface DemoRpcService {

    default String sayHello(String name) {
        return "";
    }

    /**
     * @param id
     * @return
     */
    default String checkedException(Long id) throws BizException {
        return "";
    }

    /**
     * 抛出自定义运行时异常，需要api包中有定义相关异常
     *
     * @param id
     * @return
     */
    default String runtimeException(Long id) {
        return "";
    }

}
