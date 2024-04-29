package ca.ulaval.glo2003.entities.domain.factories;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;

import java.util.UUID;

public class RestaurantFactory {
    public Restaurant create(
            String ownerId,
            String name,
            Integer capacity,
            RestaurantHours hours,
            RestaurantConfiguration reservations) {
        verifyNameNotEmpty(name);
        verifyCapacityAtLeastOne(capacity);

        return new Restaurant(
                UUID.randomUUID().toString(), ownerId, name, capacity, hours, reservations);
    }

    private void verifyNameNotEmpty(String name) {
        if (name.isEmpty()) throw new IllegalArgumentException("Restaurant name must not be empty");
    }

    private void verifyCapacityAtLeastOne(Integer capacity) {
        if (capacity < 1)
            throw new IllegalArgumentException("Restaurant minimal capacity must be one");
    }
}
