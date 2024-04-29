package ca.ulaval.glo2003.entities.api.mappers;

import ca.ulaval.glo2003.api.pojos.CustomerPojo;
import ca.ulaval.glo2003.api.pojos.ReservationTimePojo;
import ca.ulaval.glo2003.api.responses.ReservationSearchResponse;
import ca.ulaval.glo2003.domain.entities.Reservation;

import java.time.format.DateTimeFormatter;

public class ReservationSearchResponseMapper {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public ReservationSearchResponse from(Reservation reservation) {
        CustomerPojo customer =
                new CustomerPojo(
                        reservation.getCustomer().getName(),
                        reservation.getCustomer().getEmail(),
                        reservation.getCustomer().getPhoneNumber());
        ReservationTimePojo reservationTime =
                new ReservationTimePojo(
                        reservation.getReservationTime().getStart().format(timeFormatter),
                        reservation.getReservationTime().getEnd().format(timeFormatter));
        return new ReservationSearchResponse(
                reservation.getNumber(),
                reservation.getDate().format(dateFormatter),
                reservationTime,
                reservation.getGroupSize(),
                customer);
    }
}
