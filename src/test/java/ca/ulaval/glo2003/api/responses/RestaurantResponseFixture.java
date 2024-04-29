package ca.ulaval.glo2003.api.responses;

import ca.ulaval.glo2003.api.pojos.RestaurantConfigurationPojo;
import ca.ulaval.glo2003.api.pojos.RestaurantHoursPojo;

public class RestaurantResponseFixture {
    private String name = "restaurant";
    private int capacity = 12;
    private RestaurantHoursPojo hours = new RestaurantHoursPojo("10:00:00", "22:30:00");
    private RestaurantConfigurationPojo reservations = new RestaurantConfigurationPojo(60);

    public OwnerRestaurantResponse create(String restaurantId) {
        return new OwnerRestaurantResponse(restaurantId, name, capacity, hours, reservations);
    }
}
