package ca.ulaval.glo2003.entities.api.pojos;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class RestaurantHoursPojo {
    @NotNull(message = "Restaurant opening time must be provided") public String open;

    @NotNull(message = "Restaurant closing time must be provided") public String close;

    public RestaurantHoursPojo() {}

    public RestaurantHoursPojo(String open, String close) {
        this.open = open;
        this.close = close;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantHoursPojo that = (RestaurantHoursPojo) o;
        return Objects.equals(open, that.open) && Objects.equals(close, that.close);
    }

    @Override
    public int hashCode() {
        return Objects.hash(open, close);
    }
}
