package ca.ulaval.glo2003.entities.api.mappers;

import ca.ulaval.glo2003.api.responses.RestaurantResponse;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;

public class RestaurantResponseMapper {
    public RestaurantResponse fromDto(RestaurantDto restaurantDto) {
        RestaurantResponse restaurantResponse = new RestaurantResponse();

        restaurantResponse.id = restaurantDto.id;
        restaurantResponse.name = restaurantDto.name;
        restaurantResponse.hours = restaurantDto.hours;
        restaurantResponse.capacity = restaurantDto.capacity;
        restaurantResponse.reservations = restaurantDto.reservations;

        return restaurantResponse;
    }
}
