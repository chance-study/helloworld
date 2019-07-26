package org.chance.sprintboottest.web;

import org.chance.sprintboottest.domain.User;
import org.chance.sprintboottest.domain.UserRepository;
import org.chance.sprintboottest.exception.UserNameNotFoundException;
import org.chance.sprintboottest.exception.VehicleNotFoundException;
import org.chance.sprintboottest.service.VehicleDetails;
import org.chance.sprintboottest.service.VehicleDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class UserVehicleService {

    private final UserRepository userRepository;

    private final VehicleDetailsService vehicleDetailsService;

    public UserVehicleService(UserRepository userRepository, VehicleDetailsService vehicleDetailsService) {
        this.userRepository = userRepository;
        this.vehicleDetailsService = vehicleDetailsService;
    }

    public VehicleDetails getVehicleDetails(String username)
            throws UserNameNotFoundException, VehicleNotFoundException {
        Assert.notNull(username, "Username must not be null");
        User user = this.userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNameNotFoundException(username);
        }
        return this.vehicleDetailsService.getVehicleDetails(user.getVin());
    }

}
