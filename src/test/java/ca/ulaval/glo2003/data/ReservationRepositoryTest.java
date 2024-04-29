package ca.ulaval.glo2003.data;

import ca.ulaval.glo2003.data.inmemory.ReservationRepositoryInMemory;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.ReservationFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

class ReservationRepositoryTest {
    ReservationRepositoryInMemory repository;

    Reservation reservation;
    String reservationNumber;

    @BeforeEach
    void setup() {
        reservation = new ReservationFixture().create();
        reservationNumber = reservation.getNumber();

        repository = new ReservationRepositoryInMemory();
    }

    @Test
    public void givenNonEmptyRepository_whenGet_thenReturnsReservation() {
        repository.add(reservation);

        Optional<Reservation> gottenReservation = repository.get(reservationNumber);

        assertThat(gottenReservation.isPresent()).isTrue();
        assertThat(gottenReservation.get()).isEqualTo(reservation);
    }

    @Test
    public void givenEmptyRepository_whenGet_thenReturnsNull() {
        Optional<Reservation> gottenReservation = repository.get(reservationNumber);

        assertThat(gottenReservation.isEmpty()).isTrue();
    }
}
