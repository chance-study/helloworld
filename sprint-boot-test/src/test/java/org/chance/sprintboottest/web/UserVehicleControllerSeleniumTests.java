package org.chance.sprintboottest.web;

import org.chance.sprintboottest.service.VehicleDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * selenium用于Web应用程序测试的工具
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserVehicleController.class)
public class UserVehicleControllerSeleniumTests {

    /**
     * 如果你使用HtmlUnit或Selenium， 自动配置将提供一个WebClient bean和/或WebDriver bean
     */
    @Autowired
    private WebDriver webDriver;

    @MockBean
    private UserVehicleService userVehicleService;

    @Test
    public void getVehicleWhenRequestingTextShouldReturnMakeAndModel() {
        given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
        this.webDriver.get("/sboot/vehicle.html");
        WebElement element = this.webDriver.findElement(By.tagName("h1"));
        assertThat(element.getText()).isEqualTo("Honda Civic");
    }

}
