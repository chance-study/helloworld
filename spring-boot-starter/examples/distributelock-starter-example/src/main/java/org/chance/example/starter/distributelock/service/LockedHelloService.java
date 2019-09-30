package org.chance.example.starter.distributelock.service;

import org.chance.distributelock.jdbc.alias.JdbcLocked;

/**
 * LockedHelloService
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/29
 */
public class LockedHelloService implements HelloService {

    @Override
    @JdbcLocked(expression = "#name")
    public String sayHello(String name) {
        return "Hello " + name + "!";
    }
}
