package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.api.pojos.CustomerPojo;
import ca.ulaval.glo2003.domain.entities.AvailabilitiesFixture;
import ca.ulaval.glo2003.domain.entities.CustomerFixture;
import ca.ulaval.glo2003.domain.entities.ReservationFixture;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import ca.ulaval.glo2003.domain.factories.CustomerFactory;
import ca.ulaval.glo2003.domain.factories.ReservationFactory;
import jakarta.ws.rs.NotFoundException;
import org.glassfish.grizzly.utils.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    ReservationService reservationService;

    @Mock ReservationRepository reservationRepository;
    @Mock RestaurantRepository restaurantRepository;
    @Mock ReservationFactory reservationFactory;
    @Mock CustomerFactory customerFactory;

    @BeforeEach
    void setUp() {
        reservationService =
                new ReservationService(
                        reservationRepository,
                        restaurantRepository,
                        reservationFactory,
                        customerFactory);
    }

    @Test
    void whenCreateReservation_thenReturnsReservationNumber() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(customerFactory.create(CUSTOMER_NAME, EMAIL, PHONE_NUMBER)).thenReturn(CUSTOMER);
        when(reservationRepository.searchAvailabilities(RESTAURANT, LocalDate.parse(DATE)))
                .thenReturn(AVAILABILITIES);
        when(reservationFactory.create(
                        DATE, START_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT, AVAILABILITIES))
                .thenReturn(RESERVATION);

        String gottenNumber =
                reservationService.createReservation(
                        RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, CUSTOMER_POJO);

        assertThat(gottenNumber).isEqualTo(NUMBER);
    }

    @Test
    void whenCreateReservation_thenReservationIsSaved() {
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(customerFactory.create(CUSTOMER_NAME, EMAIL, PHONE_NUMBER)).thenReturn(CUSTOMER);
        when(reservationRepository.searchAvailabilities(RESTAURANT, LocalDate.parse(DATE)))
                .thenReturn(AVAILABILITIES);
        when(reservationFactory.create(
                        DATE, START_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT, AVAILABILITIES))
                .thenReturn(RESERVATION);

        reservationService.createReservation(
                RESTAURANT_ID, DATE, START_TIME, GROUP_SIZE, CUSTOMER_POJO);

        verify(reservationRepository).add(RESERVATION);
    }

    @Test
    void whenGetReservation_thenReturnsPairOfReservationAndRestaurant() {
        Pair<Reservation, Restaurant> expectedPair = new Pair<>(RESERVATION, RESTAURANT);
        when(restaurantRepository.get(RESTAURANT_ID)).thenReturn(Optional.of(RESTAURANT));
        when(reservationRepository.get(NUMBER)).thenReturn(Optional.of(RESERVATION));

        Pair<Reservation, Restaurant> gottenPair = reservationService.getReservation(NUMBER);

        assertThat(gottenPair.getFirst()).isEqualTo(expectedPair.getFirst());
        assertThat(gottenPair.getSecond()).isEqualTo(expectedPair.getSecond());
    }

    @Test
    void givenNonExistingNumber_whenGetReservation_thenThrowsNotFoundException() {
        String nonExistingNumber = "1234";
        when(reservationRepository.get(nonExistingNumber)).thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> reservationService.getReservation(nonExistingNumber));
    }

    @Test
    void whenDeleteReservation_thenReservationIsDeleted() {
        when(reservationRepository.delete(NUMBER)).thenReturn(Optional.of(RESERVATION));

        reservationService.deleteReservation(NUMBER);

        verify(reservationRepository).delete(NUMBER);
    }

    @Test
    void givenNonExistingNumber_whenDeleteReservation_thenThrowsNotFoundException() {
        String nonExistingNumber = "1234";

        assertThrows(
                NotFoundException.class,
                () -> reservationService.deleteReservation(nonExistingNumber));
    }

    private static final String NUMBER = "123456789123456789";
    private static final String DATE = LocalDate.now().plusDays(2).toString();
    private static final String START_TIME = "12:00:00";
    private static final Integer GROUP_SIZE = 3;
    private static final String RESTAURANT_ID = UUID.randomUUID().toString();

    private static final Restaurant RESTAURANT =
            new RestaurantFixture().withId(RESTAURANT_ID).create();
    private static final Reservation RESERVATION =
            new ReservationFixture().withNumber(NUMBER).withRestaurantId(RESTAURANT_ID).create();

    private static final String CUSTOMER_NAME = "Johnny Cash";
    private static final String EMAIL = "johnny.cash@example.com";
    private static final String PHONE_NUMBER = "1234567890";
    private static final Customer CUSTOMER = new CustomerFixture().create();
    private static final CustomerPojo CUSTOMER_POJO =
            new CustomerPojo(CUSTOMER_NAME, EMAIL, PHONE_NUMBER);

    private static final Map<LocalDateTime, Integer> AVAILABILITIES =
            new AvailabilitiesFixture().create();
}
