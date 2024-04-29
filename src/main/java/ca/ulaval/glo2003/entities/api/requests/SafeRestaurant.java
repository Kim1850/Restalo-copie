package ca.ulaval.glo2003.entities.api.requests;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;

public class SafeRestaurant {
    public String name;
    public Integer capacity;
    public RestaurantHours hours;
    public String id;

    public SafeRestaurant(Restaurant restaurant) {
        this.name = restaurant.getName();
        this.id = restaurant.getId();
        this.capacity = restaurant.getCapacity();
        this.hours = restaurant.getHours();
    }
}
