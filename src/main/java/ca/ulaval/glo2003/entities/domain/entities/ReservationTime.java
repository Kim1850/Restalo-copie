package ca.ulaval.glo2003.entities.domain.entities;

import java.time.LocalTime;
import java.util.Objects;

public class ReservationTime {
    private final LocalTime start;
    private final LocalTime end;

    public ReservationTime(LocalTime startTime, int duration) {
        this.start = roundToNext15Minutes(startTime);
        this.end = this.start.plusMinutes(duration);
    }

    private LocalTime roundToNext15Minutes(LocalTime time) {
        if (time.getNano() != 0) {
            time = time.plusSeconds(1);
        }
        return time.withNano(0).plusSeconds((4500 - (time.toSecondOfDay() % 3600)) % 900);
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationTime that = (ReservationTime) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }
}
