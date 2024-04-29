package ca.ulaval.glo2003.entities.api.mappers;

import ca.ulaval.glo2003.api.requests.CreateReservationRequest;
import ca.ulaval.glo2003.domain.dto.ReservationDto;

public class CreateReservationRequestMapper {
    public ReservationDto toDto(CreateReservationRequest request) {
        ReservationDto reservationDto = new ReservationDto();

        reservationDto.date = request.date;
        reservationDto.time.start = request.startTime;
        reservationDto.groupSize = request.groupSize;
        reservationDto.customer = request.customer;

        return reservationDto;
    }
}
