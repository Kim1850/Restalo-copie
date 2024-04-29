package ca.ulaval.glo2003.entities.domain.mappers;

import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RestaurantHoursMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public RestaurantHoursDto toDto(RestaurantHours restaurantHours) {
        RestaurantHoursDto dto = new RestaurantHoursDto();

        dto.open = restaurantHours.getOpen().format(dateTimeFormatter);
        dto.close = restaurantHours.getClose().format(dateTimeFormatter);

        return dto;
    }

    public RestaurantHours fromDto(RestaurantHoursDto dto) {
        LocalTime open = parseOpenHour(dto.open);
        LocalTime close = parseCloseHour(dto.close);

        return new RestaurantHours(open, close);
    }

    private LocalTime parseOpenHour(String open) {
        return LocalTime.parse(open, dateTimeFormatter);
    }

    private LocalTime parseCloseHour(String close) {
        return LocalTime.parse(close, dateTimeFormatter);
    }
}
