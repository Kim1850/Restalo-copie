package ca.ulaval.glo2003.entities.domain.entities;

import java.time.LocalTime;
import java.util.Objects;

public class RestaurantHours {
    private LocalTime open;
    private LocalTime close;

    public RestaurantHours(LocalTime open, LocalTime close) {
        this.open = open;
        this.close = close;
    }

    public LocalTime getOpen() {
        return open;
    }

    public LocalTime getClose() {
        return close;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantHours that = (RestaurantHours) o;
        return Objects.equals(open, that.open) && Objects.equals(close, that.close);
    }

    @Override
    public int hashCode() {
        return Objects.hash(open, close);
    }
}
