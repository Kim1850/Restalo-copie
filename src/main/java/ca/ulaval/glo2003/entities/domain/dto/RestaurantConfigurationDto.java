package ca.ulaval.glo2003.entities.domain.dto;

import java.util.Objects;

public class RestaurantConfigurationDto {
    public Integer duration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantConfigurationDto that = (RestaurantConfigurationDto) o;
        return Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration);
    }
}
