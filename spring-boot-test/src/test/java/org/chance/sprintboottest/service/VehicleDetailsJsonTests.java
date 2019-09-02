package org.chance.sprintboottest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 使用@JsonTest，测试对象JSON序列化和反序列化是否工作正常
 * 它将自动配置ObjectMapper，@JsonComponent，Modules，Gson等。
 */
@RunWith(SpringRunner.class)
@JsonTest
public class VehicleDetailsJsonTests {

    @Autowired
    private JacksonTester<VehicleDetails> json;

    /**
     * 序列化测试
     *
     * @throws Exception
     */
    @Test
    public void serializeJson() throws Exception {

        VehicleDetails details = new VehicleDetails("Honda", "Civic");

        assertThat(this.json.write(details)).isEqualTo("vehicledetails.json");
        assertThat(this.json.write(details)).isEqualToJson("vehicledetails.json");
        assertThat(this.json.write(details)).hasJsonPathStringValue("@.make");
        assertThat(this.json.write(details)).extractingJsonPathStringValue("@.make").isEqualTo("Honda");
    }

    /**
     * 反序列化测试
     *
     * @throws Exception
     */
    @Test
    public void deserializeJson() throws Exception {
        String content = "{\"make\":\"Ford\",\"model\":\"Focus\"}";
        assertThat(this.json.parse(content)).isEqualTo(new VehicleDetails("Ford", "Focus"));
        assertThat(this.json.parseObject(content).getMake()).isEqualTo("Ford");
    }

}
