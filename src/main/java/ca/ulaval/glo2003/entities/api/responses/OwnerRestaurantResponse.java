package ca.ulaval.glo2003.entities.api.responses;

import ca.ulaval.glo2003.api.pojos.RestaurantConfigurationPojo;
import ca.ulaval.glo2003.api.pojos.RestaurantHoursPojo;

public record OwnerRestaurantResponse(
        String id,
        String name,
        Integer capacity,
        RestaurantHoursPojo hours,
        RestaurantConfigurationPojo reservations) {}
