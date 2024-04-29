package ca.ulaval.glo2003.entities.domain.dto;

import java.util.Objects;

public class RestaurantDto {
    public String ownerId;
    public String id;
    public String name;
    public Integer capacity;
    public RestaurantHoursDto hours;
    public RestaurantConfigurationDto reservations;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDto that = (RestaurantDto) o;
        return Objects.equals(ownerId, that.ownerId)
                && Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(capacity, that.capacity)
                && Objects.equals(hours, that.hours)
                && Objects.equals(reservations, that.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, id, name, capacity, hours, reservations);
    }
}
