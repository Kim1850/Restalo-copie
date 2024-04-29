package ca.ulaval.glo2003.entities.domain.factories;

import ca.ulaval.glo2003.domain.entities.Search;
import ca.ulaval.glo2003.domain.entities.SearchOpened;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class SearchFactory {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public Search create(String name, String openedFrom, String openedTo) {
        LocalTime openedFromNotNull = parseOpenedFrom(openedFrom);
        LocalTime openedToNotNull = parseOpenedTo(openedTo);

        return new Search(name, new SearchOpened(openedFromNotNull, openedToNotNull));
    }

    private LocalTime parseOpenedFrom(String openedFrom) {
        if (Objects.nonNull(openedFrom)) {
            try {
                return LocalTime.parse(openedFrom, dateTimeFormatter);
            } catch (DateTimeParseException exception) {
                throw new IllegalArgumentException(
                        "Search 'from' time format is not valid (HH:mm:ss)");
            }
        }
        return null;
    }

    private LocalTime parseOpenedTo(String openedTo) {
        if (Objects.nonNull(openedTo)) {
            try {
                return LocalTime.parse(openedTo, dateTimeFormatter);
            } catch (DateTimeParseException exception) {
                throw new IllegalArgumentException(
                        "Search 'to' time format is not valid (HH:mm:ss)");
            }
        }
        return null;
    }
}
