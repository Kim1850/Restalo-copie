package ca.ulaval.glo2003.entities.data.mongo.entities;

import dev.morphia.annotations.Entity;

@Entity
public class RestaurantHoursMongo {
    public String open;
    public String close;

    public RestaurantHoursMongo() {}

    public RestaurantHoursMongo(String open, String close) {
        this.open = open;
        this.close = close;
    }
}
