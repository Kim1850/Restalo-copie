package ca.ulaval.glo2003.domain.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static com.google.common.truth.Truth.assertThat;

@ExtendWith(MockitoExtension.class)
class ReservationTimeTest {

    @Test
    void canCreateReservationTime() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.parse(START), DURATION);

        assertThat(reservationTime.getStart()).isEqualTo(LocalTime.parse(ROUNDED_START));
        assertThat(reservationTime.getEnd()).isEqualTo(LocalTime.parse(ROUNDED_END));
    }

    @Test
    void whenValidSecondsInputs_thenReservationTimeCreated() {
        ReservationTime reservationTime =
                new ReservationTime(LocalTime.parse(SECONDS_START), DURATION);

        assertThat(reservationTime.getStart()).isEqualTo(LocalTime.parse(ROUNDED_START));
        assertThat(reservationTime.getEnd()).isEqualTo(LocalTime.parse(ROUNDED_END));
    }

    @Test
    void whenValidNanoInputs_thenReservationTimeCreated() {
        ReservationTime reservationTime =
                new ReservationTime(LocalTime.parse(NANO_START), DURATION);

        assertThat(reservationTime.getStart()).isEqualTo(LocalTime.parse(ROUNDED_START));
        assertThat(reservationTime.getEnd()).isEqualTo(LocalTime.parse(ROUNDED_END));
    }

    private static final String START = "13:01:00";
    private static final String SECONDS_START = "13:00:01";
    private static final String NANO_START = "13:00:00.01";

    private static final Integer DURATION = 60;

    private static final String ROUNDED_START = "13:15:00";
    private static final String ROUNDED_END = "14:15:00";
}
