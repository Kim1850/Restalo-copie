package ca.ulaval.glo2003.entities.api.mappers;

import ca.ulaval.glo2003.api.requests.CreateRestaurantRequest;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;

public class CreateRestaurantRequestMapper {
    public RestaurantDto toDto(CreateRestaurantRequest request) {
        RestaurantDto restaurantDto = new RestaurantDto();

        restaurantDto.name = request.name;
        restaurantDto.capacity = request.capacity;
        // restaurantDto.hours = request.hours;
        restaurantDto.reservations = request.reservations;

        return restaurantDto;
    }
}
