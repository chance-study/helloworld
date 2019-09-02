package org.chance.sprintboottest.service;

import org.chance.sprintboottest.config.ServiceProperties;
import org.chance.sprintboottest.domain.Vehicle;
import org.chance.sprintboottest.exception.VehicleNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.HttpServerErrorException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/**
 * @RestClientTest测试REST客户端 默认会自动配置Jackson和GSON 配置RestTemplateBuilder
 * 并添加MockRestServiceServer支持
 * 需要将@RestClientTest的value或components属性值设置为待测试类
 */
@RunWith(SpringRunner.class)
@RestClientTest({RemoteVehicleDetailsService.class, ServiceProperties.class})
public class RemoteVehicleDetailsServiceTests {

    private static final String VIN = "666666";

    /**
     * 待测试的远程调用服务
     */
    @Autowired
    private RemoteVehicleDetailsService service;

    /**
     * mock的rest server
     */
    @Autowired
    private MockRestServiceServer server;

    @Test
    public void getVehicleDetailsWhenVinIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> this.service.getVehicleDetails(null)).withMessage("VIN must not be null");
    }

    @Test
    public void getVehicleDetailsWhenResultIsSuccessShouldReturnDetails() {
        this.server.expect(requestTo("/vehicle/" + VIN + "/details"))
                .andRespond(withSuccess(getClassPathResource("vehicledetails.json"), MediaType.APPLICATION_JSON));
        VehicleDetails details = this.service.getVehicleDetails(new Vehicle(VIN));
        assertThat(details.getMake()).isEqualTo("Honda");
        assertThat(details.getModel()).isEqualTo("Civic");
    }

    @Test
    public void getVehicleDetailsWhenResultIsNotFoundShouldThrowException() {
        this.server.expect(requestTo("/vehicle/" + VIN + "/details")).andRespond(withStatus(HttpStatus.NOT_FOUND));
        assertThatExceptionOfType(VehicleNotFoundException.class)
                .isThrownBy(() -> this.service.getVehicleDetails(new Vehicle(VIN)));
    }

    @Test
    public void getVehicleDetailsWhenResultIServerErrorShouldThrowException() {
        this.server.expect(requestTo("/vehicle/" + VIN + "/details")).andRespond(withServerError());
        assertThatExceptionOfType(HttpServerErrorException.class)
                .isThrownBy(() -> this.service.getVehicleDetails(new Vehicle(VIN)));
    }

    private ClassPathResource getClassPathResource(String path) {
        return new ClassPathResource(path, getClass());
    }

}
