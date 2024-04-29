package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.pojos.SearchOpenedPojo;
import ca.ulaval.glo2003.domain.entities.AvailabilitiesFixture;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import ca.ulaval.glo2003.domain.entities.TestUtils;
import ca.ulaval.glo2003.domain.factories.SearchFactory;
import jakarta.ws.rs.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    SearchService searchService;

    @Mock RestaurantRepository restaurantRepository;
    @Mock ReservationRepository reservationRepository;
    @Mock SearchFactory searchFactory;

    @BeforeEach
    void setup() {
        searchService =
                new SearchService(restaurantRepository, reservationRepository, searchFactory);
    }

    @Test
    void whenSearchRestaurants_thenReturnsRestaurantList() {
        when(searchFactory.create(RESTAURANT_NAME, OPENED_FROM, OPENED_TO)).thenReturn(SEARCH);
        when(restaurantRepository.searchRestaurants(SEARCH)).thenReturn(RESTAURANTS);

        List<Restaurant> gottenRestaurants =
                searchService.searchRestaurants(RESTAURANT_NAME, SEARCH_OPENED_POJO);

        assertThat(gottenRestaurants).isEqualTo(RESTAURANTS);
    }

    @Test
    void givenNullSearchOpened_whenSearchRestaurants_thenReturnsRestaurantList() {
        when(searchFactory.create(RESTAURANT_NAME, null, null)).thenReturn(SEARCH_NULL);
        when(restaurantRepository.searchRestaurants(SEARCH_NULL)).thenReturn(RESTAURANTS);

        List<Restaurant> gottenRestaurants = searchService.searchRestaurants(RESTAURANT_NAME, null);

        assertThat(gottenRestaurants).isEqualTo(RESTAURANTS);
    }

    @Test
    void givenEmptyRepository_whenSearchRestaurants_thenReturnsEmptyList() {
        when(searchFactory.create(RESTAURANT_NAME, OPENED_FROM, OPENED_TO)).thenReturn(SEARCH);
        when(restaurantRepository.searchRestaurants(SEARCH)).thenReturn(Collections.emptyList());

        List<Restaurant> gottenRestaurants =
                searchService.searchRestaurants(RESTAURANT_NAME, SEARCH_OPENED_POJO);

        assertThat(gottenRestaurants).isEqualTo(Collections.emptyList());
    }

    @Test
    void whenSearchReservations_thenReturnsReservationList() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reservationRepository.searchReservations(
                        RESTAURANT_ID, LocalDate.parse(DATE), CUSTOMER_NAME))
                .thenReturn(RESERVATIONS);

        List<Reservation> gottenReservations =
                searchService.searchReservations(RESTAURANT_ID, OWNER_ID, DATE, CUSTOMER_NAME);

        assertThat(gottenReservations).isEqualTo(RESERVATIONS);
    }

    @Test
    void givenRestaurantWithNoReservations_whenSearchReservations_thenReturnsEmptyList() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reservationRepository.searchReservations(
                        RESTAURANT_ID, LocalDate.parse(DATE), CUSTOMER_NAME))
                .thenReturn(Collections.emptyList());

        List<Reservation> gottenReservations =
                searchService.searchReservations(RESTAURANT_ID, OWNER_ID, DATE, CUSTOMER_NAME);

        assertThat(gottenReservations).isEqualTo(Collections.emptyList());
    }

    @Test
    void givenNonExistingRestaurant_whenSearchReservations_thenThrowsNotFoundException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () ->
                        searchService.searchReservations(
                                RESTAURANT_ID, OWNER_ID, DATE, CUSTOMER_NAME));
    }

    @Test
    void givenInvalidOwnerId_whenSearchReservations_thenThrowsNotFoundException() {
        String invalidOwnerId = "invalid_owner";
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));

        assertThrows(
                NotFoundException.class,
                () ->
                        searchService.searchReservations(
                                RESTAURANT_ID, invalidOwnerId, DATE, CUSTOMER_NAME));
    }

    @Test
    void whenSearchAvailabilities_thenReturnsAvailabilityList() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reservationRepository.searchAvailabilities(RESTAURANT, LocalDate.parse(DATE)))
                .thenReturn(AVAILABILITIES);

        List<Availability> gottenAvailabilities =
                searchService.searchAvailabilities(RESTAURANT_ID, DATE);

        assertThat(gottenAvailabilities).isEqualTo(AVAILABILITY_ENTITIES);
    }

    @Test
    void givenNonExistingRestaurant_whenSearchAvailabilities_thenThrowsNotFoundException() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> searchService.searchAvailabilities(RESTAURANT_ID, DATE));
    }

    private static final String RESTAURANT_NAME = "restaurant";
    private static final String OPENED_FROM = "10:00:00";
    private static final String OPENED_TO = "18:30:00";

    private static final SearchOpened SEARCH_OPENED =
            new SearchOpened(LocalTime.parse(OPENED_FROM), LocalTime.parse(OPENED_TO));
    private static final SearchOpenedPojo SEARCH_OPENED_POJO =
            new SearchOpenedPojo(OPENED_FROM, OPENED_TO);
    private static final Search SEARCH = new Search(RESTAURANT_NAME, SEARCH_OPENED);
    private static final Search SEARCH_NULL = new Search(null, new SearchOpened(null, null));

    private static final String RESTAURANT_ID = "123abc123abc";
    private static final String OWNER_ID = "owner";
    private static final String DATE = LocalDate.now().plusDays(2).toString();
    private static final String CUSTOMER_NAME = "Johnny Cash";

    private static final Restaurant RESTAURANT =
            new RestaurantFixture().withId(RESTAURANT_ID).create();
    private static final List<Restaurant> RESTAURANTS = TestUtils.createRestaurants(5);

    private static final List<Reservation> RESERVATIONS =
            TestUtils.createReservations(5, RESTAURANT_ID);

    private static final Map<LocalDateTime, Integer> AVAILABILITIES =
            new AvailabilitiesFixture().create();
    private static final List<Availability> AVAILABILITY_ENTITIES =
            new AvailabilitiesFixture().createList();
}
