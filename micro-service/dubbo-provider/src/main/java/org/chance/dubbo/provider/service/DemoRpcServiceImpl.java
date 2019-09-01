package org.chance.dubbo.provider.service;

import org.apache.dubbo.config.annotation.Service;
import org.chance.micro.rpc.api.dubbo.DemoRpcService;
import org.springframework.beans.factory.annotation.Value;

/**
 * DemoRpcServiceImpl
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/1
 */
@Service(interfaceClass = DemoRpcService.class, group = "")
public class DemoRpcServiceImpl implements DemoRpcService {

    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }

}
