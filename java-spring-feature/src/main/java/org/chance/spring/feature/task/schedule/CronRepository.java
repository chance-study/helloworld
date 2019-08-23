package org.chance.spring.feature.task.schedule;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * CronRepository
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/22
 */
@Repository
public interface CronRepository extends CrudRepository<Cron, String> {

}
