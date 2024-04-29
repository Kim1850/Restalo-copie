package ca.ulaval.glo2003.entities.domain.entities;

import java.util.Objects;

public class RestaurantConfiguration {
    private final Integer duration;

    public RestaurantConfiguration(Integer duration) {
        this.duration = duration;
    }

    public Integer getDuration() {
        return duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantConfiguration that = (RestaurantConfiguration) o;
        return Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration);
    }
}
