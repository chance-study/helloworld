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
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getUserByName() throws Exception {

        this.mvc.perform(get("/users/xxxxxx").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
                .andExpect(content().string(containsString("xx")));


    }

    @Test
    public void getUserByNameParam() throws Exception {

        this.mvc.perform(get("/users?name=xxxxx").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
                .andExpect(content().string(containsString("xx")));

    }

    @Test
    public void bar() throws Exception {

        this.mvc.perform(get("/users/bar?age=17").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
                .andExpect(content().string(containsString("xx")));

    }

    @Test
    public void addUser() {
    }

    @Test
    public void addUser1() {
    }

    @Test
    public void addUser2() {
    }
}