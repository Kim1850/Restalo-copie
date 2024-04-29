package ca.ulaval.glo2003.entities.domain.dto;

import java.util.Objects;

public class SearchOpenedDto {
    public String from;
    public String to;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchOpenedDto that = (SearchOpenedDto) o;
        return Objects.equals(from, that.from) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
