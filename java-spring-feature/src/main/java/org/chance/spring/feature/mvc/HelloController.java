package org.chance.spring.feature.mvc;

import org.chance.spring.feature.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2020-07-24 14:50:11
 */
@Controller
public class HelloController {

    @Autowired(required = false)
    HelloService helloService;

    @ResponseBody
    @RequestMapping("/hello")
    public String hello() {
        System.out.println(helloService); //com.fsx.service.HelloServiceImpl@512663b0
        return "hello...";
    }

}
