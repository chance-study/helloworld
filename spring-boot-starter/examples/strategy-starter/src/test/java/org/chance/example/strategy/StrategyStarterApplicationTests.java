package org.chance.example.strategy;

import org.chance.example.strategy.show.ShowProcessor;
import org.chance.example.strategy.show.ShowProcessorEnum;
import org.chance.strategy.autoconfigure.StrategyFactory;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StrategyStarterApplicationTests {

    @Rule
    public OutputCapture capture = new OutputCapture();

    @Test
    public void testOld() {
        ShowProcessor showProcessor = StrategyFactory.getInstance().createStrategy(ShowProcessorEnum.OLD, ShowProcessor.BIZ_TYPE, true);

        showProcessor.process("test");

        assertThat(capture.toString()).contains("old extra customize");
        assertThat(capture.toString()).contains("default customize");

    }

    @Test
    public void testNewcomer() {
        ShowProcessor showProcessor = StrategyFactory.getInstance().createStrategy(ShowProcessorEnum.NEWCOMER, ShowProcessor.BIZ_TYPE, true);

        showProcessor.process("test");

        assertThat(capture.toString()).contains("newcommer extra customize");
        assertThat(capture.toString()).contains("default customize");
    }

    @Test
    public void testDefault() {
        ShowProcessor showProcessor = StrategyFactory.getInstance().createStrategy(null, ShowProcessor.BIZ_TYPE, true);

        showProcessor.process("test");

        assertThat(capture.toString()).contains("default customize");
    }

    @Test
    public void testNoDefault() {

        ShowProcessor showProcessor = StrategyFactory.getInstance().createStrategy(null, ShowProcessor.BIZ_TYPE);

        assertThatNullPointerException()
                .isThrownBy(()->showProcessor.process("test"));

    }

}
