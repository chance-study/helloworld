package org.chance.dubbo.spi;

import org.apache.dubbo.common.extension.ExtensionLoader;
import org.junit.Test;

import static org.junit.Assert.*;

public class RobotTest {

    @Test
    public void sayHello() {
        ExtensionLoader<Robot> extensionLoader =
                ExtensionLoader.getExtensionLoader(Robot.class);
        Robot optimusPrime = extensionLoader.getExtension("optimusPrime");
        optimusPrime.sayHello();
        Robot bumblebee = extensionLoader.getExtension("bumblebee");
        bumblebee.sayHello();
    }
}