package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;

import java.time.LocalTime;

public class RestaurantTestUtils {
    public static RestaurantHours createRestaurantHours(String open, String close) {
        return new RestaurantHours(LocalTime.parse(open), LocalTime.parse(close));
    }

    public static RestaurantConfiguration createRestaurantReservation(int duration) {
        return new RestaurantConfiguration(duration);
    }
}
