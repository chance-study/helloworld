package org.chance.sprintboottest.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    private static final Vehicle VIN = new Vehicle("666666");

    /**
     * Data JPA 测试还会默认注入一个TestEntityManager
     * 它是标准EntityManager的替代品。
     */
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repository;

    @Test
    public void findByUsernameShouldReturnUser() {
        this.entityManager.persist(new User("sboot", VIN));
        User user = this.repository.findByUsername("sboot");
        assertThat(user.getUsername()).isEqualTo("sboot");
        assertThat(user.getVin()).isEqualTo(VIN);
    }

    @Test
    public void findByUsernameWhenNoUserShouldReturnNull() {
        this.entityManager.persist(new User("sboot", VIN));
        User user = this.repository.findByUsername("mmouse");
        assertThat(user).isNull();
    }
}