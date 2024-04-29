package ca.ulaval.glo2003.entities.domain.entities;

import java.time.LocalTime;
import java.util.Objects;

public class SearchOpened {
    private final LocalTime from;
    private final LocalTime to;

    public SearchOpened(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalTime getFrom() {
        return from;
    }

    public LocalTime getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchOpened that = (SearchOpened) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
