package org.chance.dubbo.spi.impl;


import org.chance.dubbo.spi.Robot;

/**
 * OptimusPrime
 *
 * @author GengChao &lt;catchance@163.com&gt;
 * @date 2019-09-19 13:57:12
 */
public class OptimusPrime implements Robot {

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Optimus Prime.");
    }

}
