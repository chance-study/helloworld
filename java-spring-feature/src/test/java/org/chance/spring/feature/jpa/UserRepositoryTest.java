package org.chance.spring.feature.jpa;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
//@SpringBootTest(classes = HelloServiceImplTest.Application.class)
@SpringBootTest(classes = Application.class)
@ActiveProfiles("jpa")
@Slf4j
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void userTest() {
        User user = new User();
        user.setUserName("wyk");
        user.setAge(30);
        user.setPassword("aaabbb");
        userRepository.save(user);
        User item = userRepository.findByUserName("wyk");
        log.info(item.toString());
    }

}