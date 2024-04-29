package ca.ulaval.glo2003.api.requests;

import ca.ulaval.glo2003.api.pojos.RestaurantConfigurationPojo;
import ca.ulaval.glo2003.api.pojos.RestaurantHoursPojo;

public class RestaurantRequestFixture {

    private String ownerId = "owner";
    private String name = "restaurant";
    private int capacity = 12;
    private RestaurantHoursPojo hours = new RestaurantHoursPojo("10:00:00", "22:30:00");
    private RestaurantConfigurationPojo reservations = new RestaurantConfigurationPojo(60);

    public CreateRestaurantRequest create() {
        CreateRestaurantRequest restaurantRequest = new CreateRestaurantRequest();
        restaurantRequest.name = name;
        restaurantRequest.capacity = capacity;
        restaurantRequest.hours = hours;
        restaurantRequest.reservations = reservations;
        return restaurantRequest;
    }

    public CreateRestaurantRequest withInvalidParameter(int invalidCapacity) {
        CreateRestaurantRequest restaurantRequest = new CreateRestaurantRequest();
        restaurantRequest.name = name;
        restaurantRequest.capacity = invalidCapacity;
        restaurantRequest.hours = hours;
        restaurantRequest.reservations = reservations;
        return restaurantRequest;
    }

    public CreateRestaurantRequest withMissingParameter() {
        CreateRestaurantRequest restaurantRequest = new CreateRestaurantRequest();
        restaurantRequest.capacity = capacity;
        restaurantRequest.hours = hours;
        restaurantRequest.reservations = reservations;
        return restaurantRequest;
    }
}
