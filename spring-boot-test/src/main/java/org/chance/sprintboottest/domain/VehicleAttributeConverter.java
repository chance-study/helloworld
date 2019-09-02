package org.chance.sprintboottest.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * VehicleAttributeConverter
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/7/25
 */
@Converter(autoApply = true)
public class VehicleAttributeConverter implements AttributeConverter<Vehicle, String> {

    @Override
    public String convertToDatabaseColumn(Vehicle attribute) {
        return attribute.toString();
    }

    @Override
    public Vehicle convertToEntityAttribute(String dbData) {
        return new Vehicle(dbData);
    }

}
