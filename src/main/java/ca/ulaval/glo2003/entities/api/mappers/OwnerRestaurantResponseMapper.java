package ca.ulaval.glo2003.entities.api.mappers;

import ca.ulaval.glo2003.api.pojos.RestaurantConfigurationPojo;
import ca.ulaval.glo2003.api.pojos.RestaurantHoursPojo;
import ca.ulaval.glo2003.api.responses.OwnerRestaurantResponse;
import ca.ulaval.glo2003.domain.entities.Restaurant;

import java.time.format.DateTimeFormatter;

public class OwnerRestaurantResponseMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public OwnerRestaurantResponse from(Restaurant restaurant) {
        RestaurantHoursPojo restaurantHours =
                new RestaurantHoursPojo(
                        restaurant.getHours().getOpen().format(dateTimeFormatter),
                        restaurant.getHours().getClose().format(dateTimeFormatter));
        RestaurantConfigurationPojo configuration =
                new RestaurantConfigurationPojo(restaurant.getConfiguration().getDuration());
        return new OwnerRestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getCapacity(),
                restaurantHours,
                configuration);
    }
}
