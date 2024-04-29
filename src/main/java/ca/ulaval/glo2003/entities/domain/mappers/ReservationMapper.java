package ca.ulaval.glo2003.entities.domain.mappers;

import ca.ulaval.glo2003.domain.dto.ReservationDto;
import ca.ulaval.glo2003.domain.entities.Reservation;

import java.time.format.DateTimeFormatter;

public class ReservationMapper {

    public ReservationDto toDto(Reservation reservation) {
        ReservationDto dto = new ReservationDto();

        dto.number = reservation.getNumber();
        dto.date = reservation.getDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
        dto.time = new ReservationTimeMapper().toDto(reservation.getReservationTime());
        dto.groupSize = reservation.getGroupSize();
        dto.customer = new CustomerMapper().toDto(reservation.getCustomer());
        dto.restaurant = new RestaurantMapper().toDto(reservation.getRestaurant());

        return dto;
    }
}
