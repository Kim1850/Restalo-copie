package ca.ulaval.glo2003.entities.domain.mappers;

import ca.ulaval.glo2003.domain.dto.RestaurantReservationsDto;
import ca.ulaval.glo2003.domain.entities.RestaurantReservations;

import java.util.Objects;

public class RestaurantReservationsMapper {
    public RestaurantReservationsDto toDto(RestaurantReservations reservations) {
        RestaurantReservationsDto dto = new RestaurantReservationsDto();
        dto.duration = reservations.getDuration();
        return dto;
    }

    public RestaurantReservations fromDto(RestaurantReservationsDto dto) {
        if (dto == null) {
            return new RestaurantReservations(60);
        }
        return new RestaurantReservations(Objects.requireNonNullElse(dto.duration, 60));
    }
}
