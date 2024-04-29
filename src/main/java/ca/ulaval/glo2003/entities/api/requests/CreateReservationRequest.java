package ca.ulaval.glo2003.entities.api.requests;

import ca.ulaval.glo2003.api.pojos.CustomerPojo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public class CreateReservationRequest {
    @NotNull(message = "Reservation date must be provided") public String date;

    @NotNull(message = "Reservation starting time must be provided") public String startTime;

    @NotNull(message = "Reservation group size must be provided") public Integer groupSize;

    @Valid
    @NotNull(message = "Reservation customer must be provided") public CustomerPojo customer;
}
