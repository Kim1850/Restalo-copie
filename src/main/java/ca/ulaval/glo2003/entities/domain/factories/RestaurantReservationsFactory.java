package ca.ulaval.glo2003.entities.domain.factories;

import ca.ulaval.glo2003.domain.entities.RestaurantReservations;

import java.util.Objects;

public class RestaurantReservationsFactory {
    public RestaurantReservations create(Integer duration) {
        if (Objects.isNull(duration)) return new RestaurantReservations(60);

        return new RestaurantReservations(duration);
    }
}
