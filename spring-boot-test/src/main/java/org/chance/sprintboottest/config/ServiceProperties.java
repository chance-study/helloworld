package org.chance.sprintboottest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ConfigurationProperties 读取配置文件
 */
@Component
@ConfigurationProperties
public class ServiceProperties {

    private String vehicleServiceRootUrl = "http://localhost:8080/vs";

    public String getVehicleServiceRootUrl() {
        return this.vehicleServiceRootUrl;
    }

    public void setVehicleServiceRootUrl(String vehicleServiceRootUrl) {
        this.vehicleServiceRootUrl = vehicleServiceRootUrl;
    }

}
