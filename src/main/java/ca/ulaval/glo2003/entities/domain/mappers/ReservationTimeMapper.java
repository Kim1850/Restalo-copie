package ca.ulaval.glo2003.entities.domain.mappers;

import ca.ulaval.glo2003.domain.dto.ReservationTimeDto;
import ca.ulaval.glo2003.domain.entities.ReservationTime;

import java.time.format.DateTimeFormatter;

public class ReservationTimeMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public ReservationTimeDto toDto(ReservationTime reservationTime) {
        ReservationTimeDto dto = new ReservationTimeDto();

        dto.start = reservationTime.getStart().format(dateTimeFormatter);
        dto.end = reservationTime.getEnd().format(dateTimeFormatter);

        return dto;
    }
}
