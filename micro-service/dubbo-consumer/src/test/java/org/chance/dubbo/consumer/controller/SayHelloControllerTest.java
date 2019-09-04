package org.chance.dubbo.consumer.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SayHelloControllerTest {

    /**
     * Mock MVC提供了一种强力的方式来测试MVC controllers
     * 而不用启动一个完整的HTTP server。
     */
    @Autowired
    private MockMvc mvc;


    @Test
    public void sayHello() throws Exception {

        this.mvc.perform(get("/say-hello").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
                .andExpect(content().string("Honda Civic"));

    }

    @Test
    public void validate() {
    }
}