package org.chance.sprintboottest.web;

import org.chance.sprintboottest.domain.User;
import org.chance.sprintboottest.domain.UserRepository;
import org.chance.sprintboottest.domain.Vehicle;
import org.chance.sprintboottest.exception.UserNameNotFoundException;
import org.chance.sprintboottest.service.VehicleDetails;
import org.chance.sprintboottest.service.VehicleDetailsService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/**
 * 对单个服务进行，测试不需要启动 spring boot的Application
 */
public class UserVehicleServiceTests {

    private static final Vehicle VIN = new Vehicle("666666");

    /**
     * 创建一个Mock.
     */
    @Mock
    private VehicleDetailsService vehicleDetailsService;

    /**
     * 创建一个Mock.
     */
    @Mock
    private UserRepository userRepository;

    /**
     * 被测试的服务
     *
     * @InjectMocks创建一个实例，简单的说是这个Mock可以调用真实代码的方法， 其余用@Mock（或@Spy）注解创建的mock将被注入到用该实例中。
     */
    @InjectMocks
    private UserVehicleService service;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
//        this.service = new UserVehicleService(this.userRepository, this.vehicleDetailsService);
    }

    @Test
    public void getVehicleDetailsWhenUsernameIsNullShouldThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> this.service.getVehicleDetails(null))
                .withMessage("Username must not be null");
    }

    @Test
    public void getVehicleDetailsWhenUsernameNotFoundShouldThrowException() {
        given(this.userRepository.findByUsername(anyString())).willReturn(null);
        assertThatExceptionOfType(UserNameNotFoundException.class)
                .isThrownBy(() -> this.service.getVehicleDetails("sboot"));
    }

    @Test
    public void getVehicleDetailsShouldReturnMakeAndModel() {
        given(this.userRepository.findByUsername(anyString())).willReturn(new User("sboot", VIN));
        VehicleDetails details = new VehicleDetails("Honda", "Civic");
        given(this.vehicleDetailsService.getVehicleDetails(VIN)).willReturn(details);
        VehicleDetails actual = this.service.getVehicleDetails("sboot");
        assertThat(actual).isEqualTo(details);
    }

}
