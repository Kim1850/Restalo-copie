package ca.ulaval.glo2003.entities.data.mongo.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("reservations")
public class ReservationMongo {
    @Id public String number;
    public String date;
    public ReservationTimeMongo reservationTime;
    public Integer groupSize;
    public CustomerMongo customer;
    public String restaurantId;

    public ReservationMongo() {}

    public ReservationMongo(
            String number,
            String date,
            ReservationTimeMongo reservationTime,
            Integer groupSize,
            CustomerMongo customer,
            String restaurantId) {
        this.number = number;
        this.date = date;
        this.reservationTime = reservationTime;
        this.groupSize = groupSize;
        this.customer = customer;
        this.restaurantId = restaurantId;
    }
}
