package org.chance.dubbo.provider.service;

import org.apache.dubbo.config.annotation.Service;
import org.chance.micro.rpc.api.dubbo.DemoRpcService;
import org.springframework.beans.factory.annotation.Value;

/**
 * DemoRpcServiceImpl
 * <p>
 * link org.apache.dubbo.config.annotation.Service参数说明:
 * 建议由服务提供方设置超时,在 Provider 上尽量多配置 Consumer 端属性
 * timeout 方法调用超时
 * retries 失败重试次数，缺省是 2 [2]
 * loadbalance 负载均衡算法 [3]，缺省是随机 random。还可以有轮询 roundrobin、最不活跃优先 [4] leastactive 等
 * actives 消费者端，最大并发调用限制，即当 Consumer 对一个服务的并发调用到上限后，新调用会阻塞直到超时
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/1
 */
@Service(registry = "dubboRegistry", timeout = 3000, version = "1.0",
        retries = 3, loadbalance = "random", actives = 5, group = "")
public class DemoRpcServiceImpl implements DemoRpcService {

    @Value("${dubbo.application.name}")
    private String serviceName;

    @Override
    public String sayHello(String name) {
        return String.format("[%s] : Hello, %s", serviceName, name);
    }

}
