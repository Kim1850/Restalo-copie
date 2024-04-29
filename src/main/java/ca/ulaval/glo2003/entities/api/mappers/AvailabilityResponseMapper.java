package ca.ulaval.glo2003.entities.api.mappers;

import ca.ulaval.glo2003.api.responses.AvailabilityResponse;
import ca.ulaval.glo2003.domain.entities.Availability;

import java.time.format.DateTimeFormatter;

public class AvailabilityResponseMapper {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public AvailabilityResponse from(Availability availability) {
        return new AvailabilityResponse(
                availability.getStart().format(dateTimeFormatter),
                availability.getRemainingPlaces());
    }
}
