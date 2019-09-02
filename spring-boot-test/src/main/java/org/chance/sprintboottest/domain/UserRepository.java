
package org.chance.sprintboottest.domain;

import org.springframework.data.repository.Repository;

/**
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019-07-25 17:40:08
 */
public interface UserRepository extends Repository<User, Long> {

    User findByUsername(String username);

}
