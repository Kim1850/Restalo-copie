package ca.ulaval.glo2003.entities.api.mappers;

import ca.ulaval.glo2003.api.responses.SearchRestaurantResponse;
import ca.ulaval.glo2003.domain.dto.RestaurantDto;

public class SearchRestaurantResponseMapper {
    public SearchRestaurantResponse fromDto(RestaurantDto restaurantDto) {
        SearchRestaurantResponse searchRestaurantResponse = new SearchRestaurantResponse();

        searchRestaurantResponse.id = restaurantDto.id;
        searchRestaurantResponse.name = restaurantDto.name;
        searchRestaurantResponse.hours = restaurantDto.hours;
        searchRestaurantResponse.capacity = restaurantDto.capacity;

        return searchRestaurantResponse;
    }
}
