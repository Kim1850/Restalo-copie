package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.pojos.RestaurantConfigurationPojo;
import ca.ulaval.glo2003.api.pojos.RestaurantHoursPojo;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import ca.ulaval.glo2003.domain.entities.TestUtils;
import ca.ulaval.glo2003.domain.factories.RestaurantConfigurationFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantHoursFactory;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    RestaurantService restaurantService;

    @Mock RestaurantRepository restaurantRepository;
    @Mock ReservationRepository reservationRepository;
    @Mock RestaurantFactory restaurantFactory;
    @Mock RestaurantConfigurationFactory restaurantConfigurationFactory;
    @Mock RestaurantHoursFactory restaurantHoursFactory;

    @BeforeEach
    void setUp() {
        restaurantService =
                new RestaurantService(
                        restaurantRepository,
                        reservationRepository,
                        restaurantFactory,
                        restaurantHoursFactory,
                        restaurantConfigurationFactory);
    }

    @Test
    void whenCreateRestaurant_thenReturnsRestaurantId() {
        when(restaurantHoursFactory.create(OPEN, CLOSE)).thenReturn(HOURS);
        when(restaurantConfigurationFactory.create(DURATION)).thenReturn(CONFIGURATION);
        when(restaurantFactory.create(OWNER_ID, NAME, CAPACITY, HOURS, CONFIGURATION))
                .thenReturn(RESTAURANT);

        String gottenRestaurantId =
                restaurantService.createRestaurant(
                        OWNER_ID, NAME, CAPACITY, HOURS_POJO, CONFIGURATION_POJO);

        assertThat(gottenRestaurantId).isEqualTo(RESTAURANT_ID);
    }

    @Test
    void whenCreateRestaurant_thenRestaurantIsSaved() {
        when(restaurantHoursFactory.create(OPEN, CLOSE)).thenReturn(HOURS);
        when(restaurantConfigurationFactory.create(DURATION)).thenReturn(CONFIGURATION);
        when(restaurantFactory.create(OWNER_ID, NAME, CAPACITY, HOURS, CONFIGURATION))
                .thenReturn(RESTAURANT);

        restaurantService.createRestaurant(
                OWNER_ID, NAME, CAPACITY, HOURS_POJO, CONFIGURATION_POJO);

        verify(restaurantRepository).add(RESTAURANT);
    }

    @Test
    void whenGetRestaurant_thenReturnsRestaurant() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));

        Restaurant gottenRestaurant = restaurantService.getRestaurant(RESTAURANT_ID, OWNER_ID);

        assertThat(gottenRestaurant).isEqualTo(RESTAURANT);
    }

    @Test
    void givenNonExistingRestaurantId_whenGetRestaurant_thenThrowsNotFoundException() {
        String invalidRestaurantId = "invalid_id";
        when(restaurantRepository.get(invalidRestaurantId)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> restaurantService.getRestaurant(invalidRestaurantId, OWNER_ID));
    }

    @Test
    void givenInvalidOwnerId_whenGetRestaurant_thenThrowsNotFoundException() {
        String invalidOwnerId = "not an owner";
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));

        assertThrows(
                NotFoundException.class,
                () -> restaurantService.getRestaurant(RESTAURANT_ID, invalidOwnerId));
    }

    @Test
    void whenListRestaurants_thenReturnsListOfRestaurants() {
        when(restaurantRepository.getByOwnerId(OWNER_ID)).thenReturn(RESTAURANTS);

        List<Restaurant> gottenRestaurants = restaurantService.listRestaurants(OWNER_ID);

        assertThat(gottenRestaurants).isEqualTo(RESTAURANTS);
    }

    @Test
    void givenNonExistingOwnerId_whenListRestaurants_thenReturnsEmptyList() {
        String nonExistingOwnerId = "i do not exist";
        when(restaurantRepository.getByOwnerId(nonExistingOwnerId))
                .thenReturn(Collections.emptyList());

        List<Restaurant> gottenRestaurants = restaurantService.listRestaurants(nonExistingOwnerId);

        assertThat(gottenRestaurants).isEmpty();
    }

    @Test
    void whenDeleteRestaurant_thenRestaurantIsDeleted() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(restaurantRepository.delete(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reservationRepository.deleteAll(RESTAURANT_ID)).thenReturn(List.of());

        restaurantService.deleteRestaurant(RESTAURANT_ID, OWNER_ID);

        verify(restaurantRepository).delete(RESTAURANT_ID);
        verify(reservationRepository).deleteAll(RESTAURANT_ID);
    }

    @Test
    void givenNonExistingRestaurantId_whenDeleteRestaurant_thenThrowsNotFoundException() {
        String nonExistingRestaurantId = "1234";

        assertThrows(
                NotFoundException.class,
                () -> restaurantService.deleteRestaurant(nonExistingRestaurantId, OWNER_ID));
    }

    @Test
    void givenInvalidOwnerId_whenDeleteRestaurant_thenThrowsNotFoundException() {
        String invalidOwnerId = "not an owner";

        assertThrows(
                NotFoundException.class,
                () -> restaurantService.deleteRestaurant(RESTAURANT_ID, invalidOwnerId));
    }

    private static final String RESTAURANT_ID = UUID.randomUUID().toString();
    private static final String OWNER_ID = "owner";
    private static final String NAME = "restaurant";
    private static final Integer CAPACITY = 12;

    private static final String OPEN = "10:00:00";
    private static final String CLOSE = "22:30:00";
    private static final RestaurantHours HOURS =
            new RestaurantHours(LocalTime.parse(OPEN), LocalTime.parse(CLOSE));
    private static final RestaurantHoursPojo HOURS_POJO = new RestaurantHoursPojo(OPEN, CLOSE);

    private static final int DURATION = 60;
    private static final RestaurantConfiguration CONFIGURATION =
            new RestaurantConfiguration(DURATION);
    private static final RestaurantConfigurationPojo CONFIGURATION_POJO =
            new RestaurantConfigurationPojo(DURATION);

    private static final Restaurant RESTAURANT =
            new RestaurantFixture().withId(RESTAURANT_ID).create();
    private static final List<Restaurant> RESTAURANTS = TestUtils.createRestaurants(3);
}
