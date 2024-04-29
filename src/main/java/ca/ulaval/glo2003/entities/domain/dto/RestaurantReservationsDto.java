package ca.ulaval.glo2003.entities.domain.dto;

import java.util.Objects;

public class RestaurantReservationsDto {
    public Integer duration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantReservationsDto that = (RestaurantReservationsDto) o;
        return Objects.equals(duration, that.duration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(duration);
    }
}
