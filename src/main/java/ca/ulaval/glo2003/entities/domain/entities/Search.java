package ca.ulaval.glo2003.entities.domain.entities;

import java.util.Objects;

public class Search {
    private final String name;
    private final SearchOpened searchOpened;

    public Search(String name, SearchOpened searchOpened) {
        this.name = name;
        this.searchOpened = searchOpened;
    }

    public String getName() {
        return name;
    }

    public SearchOpened getSearchOpened() {
        return searchOpened;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Search search = (Search) o;
        return Objects.equals(name, search.name)
                && Objects.equals(searchOpened, search.searchOpened);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, searchOpened);
    }
}
