package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantConfiguration;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class RestaurantFactoryTest {

    RestaurantFactory restaurantFactory;
    @Mock RestaurantConfigurationFactory restaurantConfigurationFactory;
    @Mock RestaurantHoursFactory restaurantHoursFactory;

    @BeforeEach
    void setUp() {
        restaurantFactory = new RestaurantFactory();
        lenient().when(restaurantHoursFactory.create(OPEN, CLOSE)).thenReturn(RESTAURANT_HOURS);
        lenient().when(restaurantConfigurationFactory.create(DURATION)).thenReturn(CONFIGURATION);
    }

    @Test
    void canCreateRestaurant() {
        Restaurant restaurant =
                restaurantFactory.create(OWNER_ID, NAME, CAPACITY, RESTAURANT_HOURS, CONFIGURATION);

        assertThat(restaurant).isNotNull();
        assertThat(restaurant.getOwnerId()).isEqualTo(OWNER_ID);
        assertThat(restaurant.getName()).isEqualTo(NAME);
        assertThat(restaurant.getCapacity()).isEqualTo(CAPACITY);
        assertThat(restaurant.getHours()).isEqualTo(RESTAURANT_HOURS);
        assertThat(restaurant.getConfiguration()).isEqualTo(CONFIGURATION);
    }

    @Test
    void whenNameIsEmpty_thenThrowsIllegalArgumentException() {
        String emptyName = "";

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        restaurantFactory.create(
                                OWNER_ID, emptyName, CAPACITY, RESTAURANT_HOURS, CONFIGURATION));
    }

    @Test
    void whenCapacityIsLessThan1_thenThrowsIllegalArgumentException() {
        int belowOneCapacity = -2;

        assertThrows(
                IllegalArgumentException.class,
                () ->
                        restaurantFactory.create(
                                OWNER_ID, NAME, belowOneCapacity, RESTAURANT_HOURS, CONFIGURATION));
    }

    private static final String OWNER_ID = "1234";
    private static final String NAME = "Paccini";
    private static final int CAPACITY = 34;

    private static final String OPEN = "10:24:32";
    private static final String CLOSE = "22:24:32";
    private static final RestaurantHours RESTAURANT_HOURS =
            new RestaurantHours(LocalTime.parse(OPEN), LocalTime.parse(CLOSE));

    private static final int DURATION = 120;
    private static final RestaurantConfiguration CONFIGURATION =
            new RestaurantConfiguration(DURATION);
}
