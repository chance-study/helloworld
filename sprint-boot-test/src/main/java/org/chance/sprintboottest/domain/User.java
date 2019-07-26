package org.chance.sprintboottest.domain;

import org.springframework.util.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * User
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/7/25
 */
@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private Vehicle vin;

    protected User() {

    }

    public User(String username, Vehicle vin) {
        Assert.hasLength(username, "Username must not be empty");
        Assert.notNull(vin, "VIN must not be null");
        this.username = username;
        this.vin = vin;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public Vehicle getVin() {
        return vin;
    }
}
