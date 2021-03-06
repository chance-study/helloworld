package org.chance.dubbo.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class SayHelloControllerTest {

    /**
     * Mock MVC提供了一种强力的方式来测试MVC controllers
     * 而不用启动一个完整的HTTP server。
     */
    @Autowired
    private MockMvc mvc;

    @Test
    public void sayHello() throws Exception {

        log.info("sayHello test");

        this.mvc.perform(get("/say-hello?name=xx").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
                .andExpect(content().string(containsString("xx")));

    }

    @Test
    public void validate() throws Exception {
        this.mvc.perform(get("/validate?id=0").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().string(containsString("id不能小于1")));
    }
}