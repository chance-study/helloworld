package org.chance.simple.service.impl;

import org.chance.simple.service.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintDeclarationException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloServiceImplTest {

    @Autowired
    private HelloService helloService;


    @Test
    public void hello() {

        System.out.println(helloService.getClass());
        helloService.hello(1, null);

    }

    @Test(expected = ConstraintDeclarationException.class)
//    @Test
    public void hello0() {

        System.out.println(helloService.getClass());
        helloService.hello0(1);

    }

    @Test
    public void helloReturn() {

        System.out.println(helloService.getClass());
        helloService.helloReturn(1, null);

    }
}