package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

class RestaurantConfigurationFactoryTest {
    RestaurantConfigurationFactory restaurantConfigurationFactory;

    @BeforeEach
    public void setUp() {
        restaurantConfigurationFactory = new RestaurantConfigurationFactory();
    }

    @Test
    public void givenNullDuration_whenCreate_thenDurationIs60() {
        RestaurantConfiguration restaurantConfiguration =
                restaurantConfigurationFactory.create(null);

        assertThat(restaurantConfiguration.getDuration()).isEqualTo(60);
    }

    @Test
    public void givenNonNullDuration_whenCreate_thenSpecifiedDuration() {
        Integer duration = 90;

        RestaurantConfiguration reservations = restaurantConfigurationFactory.create(duration);

        assertThat(reservations.getDuration()).isEqualTo(duration);
    }

    @Test
    public void whenDurationIsNegative_thenThrowsIllegalArgumentException() {
        Integer duration = -15;

        assertThrows(
                IllegalArgumentException.class,
                () -> restaurantConfigurationFactory.create(duration));
    }
}
