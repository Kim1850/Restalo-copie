package ca.ulaval.glo2003.entities.api.pojos;

import java.util.Objects;

public class RestaurantConfigurationPojo {
    public Integer duration;

    public RestaurantConfigurationPojo() {}

    public RestaurantConfigurationPojo(Integer duration) {
        this.duration = duration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantConfigurationPojo that = (RestaurantConfigurationPojo) o;
        return Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration);
    }
}
