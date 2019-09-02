package org.chance.sprintboottest.web;

import org.chance.sprintboottest.WelcomeCommandLineRunner;
import org.chance.sprintboottest.service.VehicleDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 可以使用@SpringBootTest的webEnvironment属性定义怎么运行测试：
 * MOCK - 加载WebApplicationContext，并提供一个mock servlet环境，使用该注解时内嵌servlet容器将不会启动。如果classpath下不存在servlet APIs，该模式将创建一个常规的non-web ApplicationContext。
 * <p>
 * RANDOM_PORT - 加载EmbeddedWebApplicationContext，并提供一个真实的servlet环境。使用该模式内嵌容器将启动，并监听在一个随机端口。
 * <p>
 * DEFINED_PORT - 加载EmbeddedWebApplicationContext，并提供一个真实的servlet环境。使用该模式内嵌容器将启动，并监听一个定义好的端口（比如application.properties中定义的或默认的8080端口）。
 * <p>
 * NONE - 使用SpringApplication加载一个ApplicationContext，但不提供任何servlet环境（不管是mock还是其他）。
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
/**
 * 使用@AutoConfigureMockMvc注解一个non-@WebMvcTest的类（比如SpringBootTest）也可以自动配置MockMvc。
 */
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class UserVehicleControllerApplicationTests {

    /**
     * Mock MVC提供了一种强力的方式来测试MVC controllers
     * 而不用启动一个完整的HTTP server。
     */
    @Autowired
    private MockMvc mvc;

    /**
     * 多种请求方式（TestRestTemplate）
     */
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 使用@mockBean注解将Mock对象添加到Spring上下文中
     * Mock将替换Spring上下文中任何相同类型的现有bean
     * 如果没有定义相同类型的bean，将添加一个新的bean。
     */
    @MockBean
    private UserVehicleService userVehicleService;

    /**
     * 自定义配置
     */
    @TestConfiguration
    static class Config {
        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder()
                    .additionalMessageConverters()
                    .customizers();
        }
    }

    @Test
    public void getVehicleWhenRequestingTextShouldReturnMakeAndModel() throws Exception {
        given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));
        this.mvc.perform(get("/sboot/vehicle").accept(MediaType.TEXT_PLAIN)).andExpect(status().isOk())
                .andExpect(content().string("Honda Civic"));
    }

    @Test
    public void getVehicleWhenRequestingTextShouldReturnMakeAndModel1() throws Exception {

        given(this.userVehicleService.getVehicleDetails("sboot")).willReturn(new VehicleDetails("Honda", "Civic"));

        String body = this.restTemplate.getForObject("/sboot/vehicle", String.class);

        assertThat(body).isEqualTo("Honda Civic");

    }

    @Test
    public void welcomeCommandLineRunnerShouldBeAvailable() {
        // Since we're a @SpringBootTest all beans should be available.
        assertThat(this.applicationContext.getBean(WelcomeCommandLineRunner.class)).isNotNull();
    }

}
