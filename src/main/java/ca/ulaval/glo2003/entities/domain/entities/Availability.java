package ca.ulaval.glo2003.entities.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Availability {
    private final LocalDateTime start;
    private final Integer remainingPlaces;

    public Availability(LocalDateTime start, Integer remainingPlaces) {
        this.start = start;
        this.remainingPlaces = remainingPlaces;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public Integer getRemainingPlaces() {
        return remainingPlaces;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Availability that = (Availability) o;
        return Objects.equals(start, that.start)
                && Objects.equals(remainingPlaces, that.remainingPlaces);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, remainingPlaces);
    }
}
