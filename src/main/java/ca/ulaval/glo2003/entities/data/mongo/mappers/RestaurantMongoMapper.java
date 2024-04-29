package ca.ulaval.glo2003.entities.data.mongo.mappers;

import ca.ulaval.glo2003.data.mongo.entities.RestaurantMongo;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;

public class RestaurantMongoMapper {

    private final RestaurantHoursMongoMapper hoursMapper = new RestaurantHoursMongoMapper();

    public RestaurantMongo toMongo(Restaurant restaurant) {
        return new RestaurantMongo(
                restaurant.getId(),
                restaurant.getOwnerId(),
                restaurant.getName(),
                restaurant.getCapacity(),
                hoursMapper.toMongo(restaurant.getHours()),
                restaurant.getConfiguration().getDuration());
    }

    public Restaurant fromMongo(RestaurantMongo restaurant) {
        return new Restaurant(
                restaurant.id,
                restaurant.ownerId,
                restaurant.name,
                restaurant.capacity,
                hoursMapper.fromMongo(restaurant.hours),
                new RestaurantConfiguration(restaurant.reservationsDuration));
    }
}
