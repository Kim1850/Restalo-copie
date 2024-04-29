package ca.ulaval.glo2003.entities.api.mappers;

import ca.ulaval.glo2003.api.pojos.RestaurantHoursPojo;
import ca.ulaval.glo2003.api.responses.UserRestaurantResponse;
import ca.ulaval.glo2003.domain.entities.Restaurant;

import java.time.format.DateTimeFormatter;

public class UserRestaurantResponseMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public UserRestaurantResponse from(Restaurant restaurant) {
        RestaurantHoursPojo restaurantHours =
                new RestaurantHoursPojo(
                        restaurant.getHours().getOpen().format(dateTimeFormatter),
                        restaurant.getHours().getClose().format(dateTimeFormatter));
        return new UserRestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCapacity(),
                restaurantHours);
    }
}
