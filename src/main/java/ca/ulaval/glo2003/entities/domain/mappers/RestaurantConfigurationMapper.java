package ca.ulaval.glo2003.entities.domain.mappers;

import ca.ulaval.glo2003.domain.dto.RestaurantConfigurationDto;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;

import java.util.Objects;

public class RestaurantConfigurationMapper {
    public RestaurantConfigurationDto toDto(RestaurantConfiguration reservations) {
        RestaurantConfigurationDto dto = new RestaurantConfigurationDto();
        dto.duration = reservations.getDuration();
        return dto;
    }

    public RestaurantConfiguration fromDto(RestaurantConfigurationDto dto) {
        if (Objects.isNull(dto)) return new RestaurantConfiguration(60);

        return new RestaurantConfiguration(Objects.requireNonNullElse(dto.duration, 60));
    }
}
