package ca.ulaval.glo2003.entities.data.mongo.mappers;

import ca.ulaval.glo2003.data.mongo.entities.ReservationMongo;
import ca.ulaval.glo2003.domain.entities.Reservation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReservationMongoMapper {

    private final ReservationTimeMongoMapper timeMapper = new ReservationTimeMongoMapper();
    private final CustomerMongoMapper customerMapper = new CustomerMongoMapper();

    public ReservationMongo toMongo(Reservation reservation) {
        return new ReservationMongo(
                reservation.getNumber(),
                reservation.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE),
                timeMapper.toMongo(reservation.getReservationTime()),
                reservation.getGroupSize(),
                customerMapper.toMongo(reservation.getCustomer()),
                reservation.getRestaurantId());
    }

    public Reservation fromMongo(ReservationMongo reservation) {
        return new Reservation(
                reservation.number,
                LocalDate.parse(reservation.date),
                timeMapper.fromMongo(reservation.reservationTime),
                reservation.groupSize,
                customerMapper.fromMongo(reservation.customer),
                reservation.restaurantId);
    }
}
