package org.chance.sprintboottest.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class VehicleTest {

    private static final String SAMPLE_VIN = "123456";

    /**
     * 测试参数错误的场景1
     */
    @Test
    public void createWhenVinIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Vehicle(null))
                .withMessage("VIN must not be null");
    }

    /**
     * 测试参数错误的场景2
     */
    @Test
    public void createWhenVinIsMoreThan6CharsShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Vehicle("1234567"))
                .withMessage("VIN must be exactly 6 characters");
    }

    /**
     * 测试参数错误的场景2
     */
    @Test
    public void createWhenVinIsLessThan6CharsShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> new Vehicle("12345"))
                .withMessage("VIN must be exactly 6 characters");
    }

    /**
     * 测试方法
     */
    @Test
    public void toStringShouldReturnVin() {
        Vehicle vin = new Vehicle(SAMPLE_VIN);
        assertThat(vin.toString()).isEqualTo(SAMPLE_VIN);
    }

    /**
     * 测试方法
     */
    @Test
    public void equalsAndHashCodeShouldBeBasedOnVin() {
        Vehicle vin1 = new Vehicle(SAMPLE_VIN);
        Vehicle vin2 = new Vehicle(SAMPLE_VIN);
        Vehicle vin3 = new Vehicle("666666");
        assertThat(vin1.hashCode()).isEqualTo(vin2.hashCode());
        assertThat(vin1).isEqualTo(vin1).isEqualTo(vin2).isNotEqualTo(vin3);
    }

}