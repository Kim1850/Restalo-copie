package ca.ulaval.glo2003.entities.domain.mappers;

import ca.ulaval.glo2003.domain.dto.SearchDto;
import ca.ulaval.glo2003.domain.entities.Search;

public class SearchMapper {
    public SearchDto toDto(Search search) {
        SearchDto dto = new SearchDto();

        dto.name = search.getName();
        dto.searchOpened = new SearchOpenedMapper().toDto(search.getSearchOpened());

        return dto;
    }
}
