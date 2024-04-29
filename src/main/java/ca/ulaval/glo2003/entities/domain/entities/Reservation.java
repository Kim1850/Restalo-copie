package ca.ulaval.glo2003.entities.domain.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Reservation {
    private final String number;
    private final LocalDate date;
    private final ReservationTime reservationTime;
    private final Integer groupSize;
    private final Customer customer;
    private final String restaurantId;

    public Reservation(
            String number,
            LocalDate date,
            ReservationTime reservationTime,
            Integer groupSize,
            Customer customer,
            String restaurantId) {
        this.number = number;
        this.date = date;
        this.reservationTime = reservationTime;
        this.groupSize = groupSize;
        this.customer = customer;
        this.restaurantId = restaurantId;
    }

    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public ReservationTime getReservationTime() {
        return reservationTime;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(number, that.number)
                && Objects.equals(date, that.date)
                && Objects.equals(reservationTime, that.reservationTime)
                && Objects.equals(groupSize, that.groupSize)
                && Objects.equals(customer, that.customer)
                && Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, date, reservationTime, groupSize, customer, restaurantId);
    }
}
