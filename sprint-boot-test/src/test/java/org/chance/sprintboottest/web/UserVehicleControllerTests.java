package org.chance.sprintboottest.web;

import org.chance.sprintboottest.WelcomeCommandLineRunner;
import org.chance.sprintboottest.domain.Vehicle;
import org.chance.sprintboottest.exception.UserNameNotFoundException;
import org.chance.sprintboottest.exception.VehicleNotFoundException;
import org.chance.sprintboottest.service.VehicleDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @WebMvcTest 测试Spring MVC controller可以使用@WebMvcTest，它会自动配置MockMvc，Mock MVC提供了一种强力的方式来测试MVC controllers，而不用启动一个完整的HTTP server。
 * @WebMvcTest只对 SpringMvc 进行,不会启动完成 spring boot 引用
 * @WebMvcTest 将自动配置Spring MVC设施，并且只扫描注解@Controller，@ControllerAdvice，@JsonComponent，Filter，WebMvcConfigurer和HandlerMethodArgumentResolver的beans，
 * 其他常规的@Component beans将不会被扫描。
 */
@RunWith(SpringRunner.class)
@WebMvcTest(UserVehicleController.class)
public class UserVehicleControllerTests {

    private static final Vehicle VIN = new Vehicle("666666");

    @Autowired
    private MockMvc mvc;

    /**
     * ApplicationContext spring的上下文，可以直接注入
     */
    @Autowired
    private ApplicationContext applicationContext;

    @MockBean
    private UserVehicleService userVehicleService;

    @Test
    public void getVehicleWhenRequestingTextShouldReturnMakeAndModel() throws Exception {
        given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
        this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
                .andExpect(content().string("Honda Civic"));
    }

    @Test
    public void getVehicleWhenRequestingJsonShouldReturnMakeAndModel() throws Exception {
        given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
        this.mvc.perform(get("/sboot/vehicle").accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(content().json("{'make':'Honda','model':'Civic'}"));
    }

    @Test
    public void getVehicleWhenRequestingHtmlShouldReturnMakeAndModel() throws Exception {
        given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
        this.mvc.perform(get("/sboot/vehicle.html").accept(MediaType.TEXT_HTML)).andExpect(status().isOk())
                .andExpect(content().string(containsString("<h1>Honda Civic</h1>")));
    }

    @Test
    public void getVehicleWhenUserNotFoundShouldReturnNotFound() throws Exception {
        given(this.userVehicleService.getVehicleDetails("sboot")).willThrow(new UserNameNotFoundException("sboot"));
        this.mvc.perform(get("/sboot/vehicle")).andExpect(status().isNotFound());
    }

    @Test
    public void getVehicleWhenVinNotFoundShouldReturnNotFound() throws Exception {
        given(this.userVehicleService.getVehicleDetails("sboot"))
                .willThrow(new VehicleNotFoundException(VIN));
        this.mvc.perform(get("/sboot/vehicle")).andExpect(status().isNotFound());
    }

    @Test(expected = NoSuchBeanDefinitionException.class)
    public void welcomeCommandLineRunnerShouldBeAvailable() {
        // Since we're a @WebMvcTest WelcomeCommandLineRunner should not be available.
        this.applicationContext.getBean(WelcomeCommandLineRunner.class);
    }

}
