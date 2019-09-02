package org.chance.sprintboottest.exception;

import org.chance.sprintboottest.domain.Vehicle;

public class VehicleNotFoundException extends RuntimeException {

    private final Vehicle vehicle;

    public VehicleNotFoundException(Vehicle vin) {
        this(vin, null);
    }

    public VehicleNotFoundException(Vehicle vin, Throwable cause) {
        super("Unable to find VehicleIdentificationNumber " + vin, cause);
        this.vehicle = vin;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

}
