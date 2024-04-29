package ca.ulaval.glo2003.entities.domain.entities;

import java.util.Objects;

public class Restaurant {
    private final String ownerId;
    private final String id;
    private final String name;
    private final Integer capacity;
    private final RestaurantHours hours;
    private final RestaurantConfiguration configuration;

    public Restaurant(
            String id,
            String ownerId,
            String name,
            Integer capacity,
            RestaurantHours hours,
            RestaurantConfiguration configuration) {
        this.id = id;
        this.ownerId = ownerId;
        this.name = name;
        this.capacity = capacity;
        this.hours = hours;
        this.configuration = configuration;
    }

    public String getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public RestaurantHours getHours() {
        return hours;
    }

    public RestaurantConfiguration getConfiguration() {
        return configuration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Objects.equals(ownerId, that.ownerId)
                && Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(capacity, that.capacity)
                && Objects.equals(hours, that.hours)
                && Objects.equals(configuration, that.configuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, id, name, capacity, hours, configuration);
    }
}
