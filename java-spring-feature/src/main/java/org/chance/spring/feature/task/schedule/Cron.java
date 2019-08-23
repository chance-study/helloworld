package org.chance.spring.feature.task.schedule;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Cron
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/8/22
 */
@Entity
@Data
public class Cron {

    @Id
    private String cronId;

    private String cron;

}
