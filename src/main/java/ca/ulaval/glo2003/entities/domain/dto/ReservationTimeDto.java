package ca.ulaval.glo2003.entities.domain.dto;

import java.util.Objects;

public class ReservationTimeDto {
    public String start;
    public String end;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationTimeDto that = (ReservationTimeDto) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
