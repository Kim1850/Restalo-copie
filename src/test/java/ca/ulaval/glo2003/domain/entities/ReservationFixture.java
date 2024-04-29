package ca.ulaval.glo2003.domain.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class ReservationFixture {
    private String number = UUID.randomUUID().toString();
    private LocalDate date = LocalDate.now().plusDays(2);
    private ReservationTime reservationTime = new ReservationTime(LocalTime.parse("12:00:00"), 60);
    private Integer groupSize = 3;
    private Customer customer = new CustomerFixture().create();
    private String restaurantId = UUID.randomUUID().toString();

    public Reservation create() {
        return new Reservation(number, date, reservationTime, groupSize, customer, restaurantId);
    }

    public ReservationFixture withNumber(String number) {
        this.number = number;
        return this;
    }

    public ReservationFixture withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public ReservationFixture withReservationTime(LocalTime start, Integer duration) {
        this.reservationTime = new ReservationTime(start, duration);
        return this;
    }

    public ReservationFixture withGroupSize(Integer groupSize) {
        this.groupSize = groupSize;
        return this;
    }

    public ReservationFixture withCustomer(Customer customer) {
        this.customer = customer;
        return this;
    }

    public ReservationFixture withRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
        return this;
    }
}
