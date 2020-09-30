package org.chance.spring.feature.cache;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = HelloServiceImplTest.Application.class)
@SpringBootTest(classes = Application.class)
@Slf4j
public class CacheServiceImplTest {

    @Autowired
    private CacheService cacheService;

    @Test
    public void fun() {

        String $1st = cacheService.fun("key");

        String $2nd = cacheService.fun("key");

    }
}