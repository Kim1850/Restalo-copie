package ca.ulaval.glo2003.entities.data.mongo.mappers;

import ca.ulaval.glo2003.data.mongo.entities.RestaurantHoursMongo;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class RestaurantHoursMongoMapper {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public RestaurantHoursMongo toMongo(RestaurantHours restaurantHours) {
        String open = restaurantHours.getOpen().format(dateTimeFormatter);
        String close = restaurantHours.getClose().format(dateTimeFormatter);
        return new RestaurantHoursMongo(open, close);
    }

    public RestaurantHours fromMongo(RestaurantHoursMongo restaurantHours) {
        LocalTime open = LocalTime.parse(restaurantHours.open);
        LocalTime close = LocalTime.parse(restaurantHours.close);
        return new RestaurantHours(open, close);
    }
}
