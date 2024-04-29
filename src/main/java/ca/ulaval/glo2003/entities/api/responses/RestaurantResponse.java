package ca.ulaval.glo2003.entities.api.responses;

import ca.ulaval.glo2003.domain.dto.RestaurantConfigurationDto;
import ca.ulaval.glo2003.domain.dto.RestaurantHoursDto;

import java.util.Objects;

public class RestaurantResponse {
    public String id;
    public String name;
    public Integer capacity;
    public RestaurantHoursDto hours;
    public RestaurantConfigurationDto reservations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantResponse that = (RestaurantResponse) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(capacity, that.capacity)
                && Objects.equals(hours, that.hours)
                && Objects.equals(reservations, that.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, hours, reservations);
    }
}
