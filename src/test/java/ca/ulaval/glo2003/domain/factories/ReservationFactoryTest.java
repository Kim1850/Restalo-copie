package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.AvailabilitiesFixture;
import ca.ulaval.glo2003.domain.entities.CustomerFixture;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

@ExtendWith(MockitoExtension.class)
class ReservationFactoryTest {

    ReservationFactory reservationFactory;

    @BeforeEach
    void setUp() {
        reservationFactory = new ReservationFactory();
    }

    @Test
    void canCreateReservation() {
        Reservation reservation =
                reservationFactory.create(
                        DATE, START_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT, AVAILABILITIES);

        assertThat(reservation.getDate()).isEqualTo(LocalDate.parse(DATE));
        assertThat(reservation.getReservationTime()).isEqualTo(RESERVATION_TIME);
        assertThat(reservation.getGroupSize()).isEqualTo(GROUP_SIZE);
        assertThat(reservation.getCustomer()).isEqualTo(CUSTOMER);
        assertThat(reservation.getRestaurantId()).isEqualTo(RESTAURANT.getId());
    }

    @Test
    void whenGroupSizeBelowOne_thenThrowsIllegalArgumentException() {
        int invalidGroupSize = 0;

        assertThrowsWhenCreate(
                DATE, START_TIME, invalidGroupSize, CUSTOMER, RESTAURANT, AVAILABILITIES);
    }

    @Test
    void whenStartTimeFormatIsInvalid_thenThrowsIllegalArgumentException() {
        String invalidStartTime = "23-00-23";

        assertThrowsWhenCreate(
                DATE, invalidStartTime, GROUP_SIZE, CUSTOMER, RESTAURANT, AVAILABILITIES);
    }

    @Test
    void whenReservationStartsBeforeRestaurantOpen_thenThrowsIllegalArgumentException() {
        String earlyStartTime = "05:00:00";

        assertThrowsWhenCreate(
                DATE, earlyStartTime, GROUP_SIZE, CUSTOMER, RESTAURANT, AVAILABILITIES);
    }

    @Test
    void whenReservationEndsAfterRestaurantCloses_thenThrowsIllegalArgumentException() {
        String lateStartTime = "22:00:00";

        assertThrowsWhenCreate(
                DATE, lateStartTime, GROUP_SIZE, CUSTOMER, RESTAURANT, AVAILABILITIES);
    }

    @Test
    void whenDateFormatIsInvalid_thenThrowsIllegalArgumentException() {
        String invalidDate = "01-2024-09";

        assertThrowsWhenCreate(
                invalidDate, START_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT, AVAILABILITIES);
    }

    @Test
    void whenNoAvailabilities_thenThrowsIllegalArgumentException() {
        Map<LocalDateTime, Integer> noAvailabilities =
                new AvailabilitiesFixture().on(LocalDate.parse(DATE)).withCapacity(0).create();

        assertThrowsWhenCreate(
                DATE, START_TIME, GROUP_SIZE, CUSTOMER, RESTAURANT, noAvailabilities);
    }

    private void assertThrowsWhenCreate(
            String date,
            String startTime,
            Integer groupSize,
            Customer customer,
            Restaurant restaurant,
            Map<LocalDateTime, Integer> availabilities) {
        assertThrows(
                IllegalArgumentException.class,
                () ->
                        reservationFactory.create(
                                date, startTime, groupSize, customer, restaurant, availabilities));
    }

    private static final String DATE = "2024-03-01";
    private static final String START_TIME = "13:38:59";
    private static final Integer GROUP_SIZE = 4;

    private static final Restaurant RESTAURANT = new RestaurantFixture().create();
    private static final Customer CUSTOMER = new CustomerFixture().create();
    private static final ReservationTime RESERVATION_TIME =
            new ReservationTime(LocalTime.parse(START_TIME), 60);
    private static final Map<LocalDateTime, Integer> AVAILABILITIES =
            new AvailabilitiesFixture().on(LocalDate.parse(DATE)).create();
}
