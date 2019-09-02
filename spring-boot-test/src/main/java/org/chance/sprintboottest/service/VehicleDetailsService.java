package org.chance.sprintboottest.service;

import org.chance.sprintboottest.domain.Vehicle;
import org.chance.sprintboottest.exception.VehicleNotFoundException;

/**
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019-07-25 19:25:51
 */
public interface VehicleDetailsService {

    VehicleDetails getVehicleDetails(Vehicle vin) throws VehicleNotFoundException;

}
