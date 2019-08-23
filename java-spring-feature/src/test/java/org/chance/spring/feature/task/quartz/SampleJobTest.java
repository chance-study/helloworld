package org.chance.spring.feature.task.quartz;

import org.chance.spring.feature.JavaSpringFeatureApplication;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.ConfigurableApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

public class SampleJobTest {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Test
    public void quartzJobIsTriggered() throws InterruptedException {

        try (ConfigurableApplicationContext context = SpringApplication.run(JavaSpringFeatureApplication.class)) {
            long end = System.currentTimeMillis() + 5000;
            while ((!this.outputCapture.toString().contains("Hello World!")) && System.currentTimeMillis() < end) {
                Thread.sleep(100);
            }
            assertThat(this.outputCapture.toString()).contains("Hello World!");
        }

    }

}