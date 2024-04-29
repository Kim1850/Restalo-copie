package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Search;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SearchFactoryTest {

    SearchFactory searchFactory;

    @BeforeEach
    void setup() {
        searchFactory = new SearchFactory();
    }

    @Test
    void canCreateSearch() {
        Search search = searchFactory.create(NAME, OPENED_FROM, OPENED_TO);

        assertThat(search.getName()).isEqualTo(NAME);
        assertThat(search.getSearchOpened().getFrom()).isEqualTo(LocalTime.parse(OPENED_FROM));
        assertThat(search.getSearchOpened().getTo()).isEqualTo(LocalTime.parse(OPENED_TO));
    }

    @Test
    void canCreateSearchWithNullName() {
        Search search = searchFactory.create(null, OPENED_FROM, OPENED_TO);

        assertThat(search.getName()).isEqualTo(null);
    }

    @Test
    void canCreateSearchWithNullOpenedFrom() {
        Search search = searchFactory.create(NAME, null, OPENED_TO);

        assertThat(search.getSearchOpened().getFrom()).isEqualTo(null);
    }

    @Test
    void canCreateSearchWithNullOpenedTo() {
        Search search = searchFactory.create(NAME, OPENED_FROM, null);

        assertThat(search.getSearchOpened().getTo()).isEqualTo(null);
    }

    @Test
    void whenOpenedFromFormatIsInvalid_thenThrowsIllegalArgumentException() {
        String invalidOpenedFrom = "10 o clock";

        assertThrows(
                IllegalArgumentException.class,
                () -> searchFactory.create(NAME, invalidOpenedFrom, OPENED_TO));
    }

    @Test
    void whenOpenedToFormatIsInvalid_thenThrowsIllegalArgumentException() {
        String invalidOpenedTo = "invalid hour";

        assertThrows(
                IllegalArgumentException.class,
                () -> searchFactory.create(NAME, OPENED_FROM, invalidOpenedTo));
    }

    private static final String NAME = "My restaurant";
    private static final String OPENED_FROM = "10:00:00";
    private static final String OPENED_TO = "20:00:00";
}
