package org.chance.java.spi.impl;

import org.chance.java.spi.Robot;

/**
 * Bumblebee
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2019-09-19 13:56:58
 */
public class Bumblebee implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Bumblebee.");
    }

}
