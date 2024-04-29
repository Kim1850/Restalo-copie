package ca.ulaval.glo2003.entities.domain.dto;

import java.util.Objects;

public class SearchDto {
    public String name;
    public SearchOpenedDto searchOpened;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchDto searchDto = (SearchDto) o;
        return Objects.equals(name, searchDto.name)
                && Objects.equals(searchOpened, searchDto.searchOpened);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, searchOpened);
    }
}
