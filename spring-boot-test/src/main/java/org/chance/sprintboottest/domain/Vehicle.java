package org.chance.sprintboottest.domain;

import org.springframework.util.Assert;

/**
 * Vehicle
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/7/25
 */
public class Vehicle {

    private String vin;

    public Vehicle(String vin) {
        Assert.notNull(vin, "VIN must not be null");
        Assert.isTrue(vin.length() == 6, "VIN must be exactly 6 characters");
        this.vin = vin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        return vin != null ? vin.equals(vehicle.vin) : vehicle.vin == null;
    }

    @Override
    public int hashCode() {
        return this.vin.hashCode();
    }

    @Override
    public String toString() {
        return this.vin;
    }
}
