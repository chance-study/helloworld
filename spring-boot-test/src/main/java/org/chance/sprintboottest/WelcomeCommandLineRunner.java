package org.chance.sprintboottest;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner 在服务启动的时候就去做一些操作
 * 执行时机为容器启动完成的时候
 * 可以使用 @Order 来定义执行顺序
 *
 * @Order 注解的执行优先级是按value值从小到大顺序。
 * <p>
 * ApplicationRunner也有类似的功能
 */
@Component
public class WelcomeCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("***** WELCOME TO THE DEMO *****");
    }

}
