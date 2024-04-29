package ca.ulaval.glo2003.entities.data;

import ca.ulaval.glo2003.domain.entities.Reservation;

import java.util.HashMap;
import java.util.Map;

public class ReservationRepository {

    private final Map<String, Reservation> reservationIdToReservation;

    public ReservationRepository() {
        reservationIdToReservation = new HashMap<>();
    }

    public void add(Reservation reservation) {
        reservationIdToReservation.put(reservation.getNumber(), reservation);
    }

    public Reservation get(String reservationId) {
        return reservationIdToReservation.get(reservationId);
    }
}
