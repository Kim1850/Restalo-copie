package ca.ulaval.glo2003.entities.domain.dto;

import java.util.Objects;

public class ReservationDto {
    public String number;
    public String date;
    public ReservationTimeDto time;
    public Integer groupSize;
    public CustomerDto customer;
    public RestaurantDto restaurant;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationDto that = (ReservationDto) o;
        return Objects.equals(number, that.number)
                && Objects.equals(date, that.date)
                && Objects.equals(time, that.time)
                && Objects.equals(groupSize, that.groupSize)
                && Objects.equals(customer, that.customer)
                && Objects.equals(restaurant, that.restaurant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, time, groupSize, customer, restaurant);
    }
}
