package ca.ulaval.glo2003.entities.data.mongo.entities;

import dev.morphia.annotations.Entity;

@Entity
public class ReservationTimeMongo {
    public String start;
    public String end;

    public ReservationTimeMongo() {}

    public ReservationTimeMongo(String start, String end) {
        this.start = start;
        this.end = end;
    }
}
