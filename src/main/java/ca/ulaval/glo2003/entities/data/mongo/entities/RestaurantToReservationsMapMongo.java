package ca.ulaval.glo2003.entities.data.mongo.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.ArrayList;

@Entity
public class RestaurantToReservationsMapMongo {
    @Id public String restaurantId;
    public ArrayList<ReservationMongo> reservations;

    public RestaurantToReservationsMapMongo() {}

    public RestaurantToReservationsMapMongo(String restaurantId) {
        this.restaurantId = restaurantId;
        this.reservations = new ArrayList<ReservationMongo>();
    }

    public void add(ReservationMongo reservation) {
        reservations.add(reservation);
    }

    public Boolean remove(ReservationMongo reservation) {
        return reservations.remove(reservation);
    }
}
