package org.chance.check;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class DuplicateTest {

    static {
        /**
         * 检查重复的jar包
         * 有多个版本的相同jar包，会出现新版本的 A 类，调用了旧版本的 B 类，而且和JVM加载顺序有关，问题带有偶然性，误导性，遇到这种莫名其妙的问题，最头疼，所以，第一条，先把它防住，在每个 jar 包中挑一个一定会加载的类，加上重复类检查
         */
//        Duplicate.checkDuplicate(DuplicateTest.class);

        /**
         * 配置文件加载错，也是经常碰到的问题。用户通常会和你说：“我配置的很正确啊，不信我发给你看下，但就是报错”。然后查一圈下来，原来他发过来的配置根本没加载，平台很多产品都会在 classpath 下放一个约定的配置，如果项目中有多个，通常会取JVM加载的第一个，为了不被这么低级的问题折腾，和上面的重复jar包一样，在配置加载的地方，加上：
         */
//        Duplicate.checkDuplicate("xxx.properties");
    }

    @Test
    public void test() {
        log.info("test");
    }

}