package org.chance.spring.feature.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.chance.spring.feature.jpa.User;
import org.chance.spring.feature.jpa.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = HelloServiceImplTest.Application.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("mybatis")
@Slf4j
public class UserMapperTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    public void userTest() {
        User user = new User();
        user.setUserName("wyk");
        user.setAge(30);
        user.setPassword("aaabbb");
        userRepository.save(user);
        User user1 = userRepository.findByUserName("wyk");
        log.info(user1.toString());

        List<Map<String, String>> result = userMapper.findAll();
        log.info(result.toString());

        User user2 = userMapper.findUserById(1L);
        log.info(user2.toString());

    }

}