package org.chance.dubbo.provider.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.chance.micro.rpc.api.dubbo.ValidationParameter;
import org.chance.micro.rpc.api.dubbo.ValidationService;

/**
 * ValidationServiceImpl
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/2
 */
@Slf4j
@Service(registry = "dubboRegistry", timeout = 3000, version = "1.0",
        retries = 3, loadbalance = "random", actives = 5, group = "", validation = "true")
public class ValidationServiceImpl implements ValidationService {

    @Override
    public void save(ValidationParameter parameter) {
        log.info("save {}", parameter);
    }

    @Override
    public void delete(int id) {
        log.info("delete {}", id);
    }
}
