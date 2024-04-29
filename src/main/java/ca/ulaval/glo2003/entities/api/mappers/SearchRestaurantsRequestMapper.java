package ca.ulaval.glo2003.entities.api.mappers;

import ca.ulaval.glo2003.api.requests.SearchRestaurantsRequest;
import ca.ulaval.glo2003.domain.dto.SearchDto;

public class SearchRestaurantsRequestMapper {
    public SearchDto toDto(SearchRestaurantsRequest request) {
        SearchDto searchDto = new SearchDto();

        searchDto.name = request.name;
        searchDto.searchOpened.from = request.opened.from;
        searchDto.searchOpened.to = request.opened.to;

        return searchDto;
    }
}
