package org.chance.sprintboottest.util;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.rule.OutputCapture;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * TestUtil
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/7/26
 */
public class TestUtil {

    private static final Logger log = LoggerFactory
        .getLogger(TestUtil.class);

    /**
     * OutputCapture是JUnit的一个Rule，用于捕获System.out和System.err输出，只需简单的将@Rule注解capture，然后在断言中调用toString()：
     */
    @Rule
    public OutputCapture capture = new OutputCapture();

    @Test
    public void testName() throws Exception {

        System.out.println("Hello World!");

        log.info("r");

        // hamcrest
        assertThat(capture.toString(), containsString("World"));

        // assertJ

        Assertions.assertThat(capture.toString()).contains("r");

    }

}
