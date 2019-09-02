package org.chance.sprintboottest.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

/**
 * @DataJpaTest可以用来测试JPA，默认它会使用一个嵌入式内存数据库。 扫描@Entity类和JAP repository。
 * Data JPA测试类是事务型的，默认在每个测试结束后回滚
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserTest {

    private static final Vehicle VIN = new Vehicle("666666");

    /**
     * Data JPA 测试还会默认注入一个TestEntityManager
     * 它是标准EntityManager的替代品。
     * <p>
     * 如果想在@DataJpaTest 外使用TestEntityManager，你可以使用@AutoConfigureTestEntityManager注解。
     */
    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void createWhenUsernameIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new User(null, VIN))
                .withMessage("Username must not be empty");
    }

    @Test
    public void createWhenUsernameIsEmptyShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new User("", VIN))
                .withMessage("Username must not be empty");
    }

    @Test
    public void createWhenVinIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new User("sboot", null))
                .withMessage("VIN must not be null");
    }

    @Test
    public void saveShouldPersistData() {
        User user = this.entityManager.persistFlushFind(new User("sboot", VIN));
        assertThat(user.getUsername()).isEqualTo("sboot");
        assertThat(user.getVin()).isEqualTo(VIN);
    }

}