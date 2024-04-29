package ca.ulaval.glo2003.entities.data.mongo.entities;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

@Entity("restaurants")
public class RestaurantMongo {
    @Id public String id;
    public String ownerId;
    public String name;
    public Integer capacity;
    public RestaurantHoursMongo hours;
    public Integer reservationsDuration;

    public RestaurantMongo() {}

    public RestaurantMongo(
            String id,
            String ownerId,
            String name,
            Integer capacity,
            RestaurantHoursMongo hours,
            Integer reservationsDuration) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.reservationsDuration = reservationsDuration;
    }
}
