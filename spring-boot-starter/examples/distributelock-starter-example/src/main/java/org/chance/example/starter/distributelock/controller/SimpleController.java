package org.chance.example.starter.distributelock.controller;
import org.chance.example.starter.distributelock.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    private final HelloService helloService;

    @Autowired
    public SimpleController(final HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable("name") final String name) {
        return helloService.sayHello(name);
    }
}
