package org.chance.java.spi;

import org.junit.Test;

import java.util.ServiceLoader;

import static org.junit.Assert.*;

public class RobotTest {

    @Test
    public void sayHello() {
        ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
        System.out.println("Java SPI");
        serviceLoader.forEach(Robot::sayHello);
    }
}