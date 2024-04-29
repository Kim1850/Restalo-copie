package ca.ulaval.glo2003.entities.domain.factories;

import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;

import java.util.Objects;

public class RestaurantConfigurationFactory {
    public RestaurantConfiguration create(Integer duration) {
        if (Objects.isNull(duration)) return new RestaurantConfiguration(60);

        verifyDurationIsPositive(duration);

        return new RestaurantConfiguration(duration);
    }

    private void verifyDurationIsPositive(Integer duration) {
        if (duration < 0) {
            throw new IllegalArgumentException("Reservation duration must be positive");
        }
    }
}
