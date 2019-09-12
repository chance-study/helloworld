package org.chance.dubbo.consumer.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * gradle test命令要求 outputCapture 必须是public 否则 初始化失败
     *
     */
    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void getUserByName() throws Exception {

        this.mvc.perform(get("/users/xxxxxx").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().string(containsString("用户名格式有误")));


    }

    @Test
    public void getUserByNameParam() throws Exception {

        this.mvc.perform(get("/users?name=xxxxx").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().string(containsString("用户名格式有误")));

    }

    @Test
    public void bar() throws Exception {

        this.mvc.perform(get("/users/bar?age=17").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().string(containsString("must be greater than or equal to 18")));

    }

    @Test
    public void addUser() throws Exception {

        this.mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content("{}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("用户名不能为空")))
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message", containsString("用户名不能为空")))
        ;

    }

    @Test
    public void addUser1() throws Exception {
        this.mvc.perform(post("/users/add").contentType(MediaType.APPLICATION_JSON).content("{}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", nullValue()))
                .andExpect(jsonPath("$.age", is(0)))
        ;

        assertThat(outputCapture.toString()).contains("prop不能为null");
        assertThat(outputCapture.toString()).contains("不能为null");
        assertThat(outputCapture.toString()).contains("用户名不能为空");
    }

    @Test
    public void addUser2() throws Exception {
        this.mvc.perform(post("/users/add2").contentType(MediaType.APPLICATION_JSON).content("{}").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("用户名不能为空")))
                .andExpect(jsonPath("$.code").exists())
                .andExpect(jsonPath("$.message", containsString("用户名不能为空")))
        ;
    }
}