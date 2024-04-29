package ca.ulaval.glo2003.entities.api.requests;

import ca.ulaval.glo2003.domain.entities.Customer;

public class ReservationRequest {
    public String date;
    public String startTime;
    public Integer groupSize;
    public Customer customer;

    public ReservationRequest() {}
}
