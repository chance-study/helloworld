package org.chance.dubbo.consumer.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.chance.micro.rpc.api.dubbo.DemoRpcService;
import org.chance.micro.rpc.api.dubbo.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * SayHelloController
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/1
 */
@RestController
public class SayHelloController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Reference(version = "1.0",
            interfaceClass = DemoRpcService.class,
            check = false, group = "")
    private DemoRpcService demoRpcService;

    @RequestMapping(value = "/say-hello", method = GET)
    public String sayHello(@RequestParam String name) {
        return demoRpcService.sayHello(name);
    }


    @Reference(version = "1.0", check = false, group = "", validation = "true")
    private ValidationService validationService;

    @RequestMapping(value = "/validate", method = GET)
    public String validate(@RequestParam Integer id) {
        validationService.delete(id);
        return "delete";
    }

    /**
     * ApplicationRunner 的执行时机为容器启动完成的时候
     *
     *
     * @return
     */
    @Bean
    public ApplicationRunner runner() {
        return args -> {
            try{
                logger.info(demoRpcService.sayHello("test"));
            }catch (Exception e) {
                logger.error(e.getMessage());
            }
        };
    }

}