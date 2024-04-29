package ca.ulaval.glo2003.entities.domain.factories;

import ca.ulaval.glo2003.domain.entities.RestaurantHours;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RestaurantHoursFactory {
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public RestaurantHours create(String open, String close) {
        LocalTime parsedOpen = parseOpenHour(open);
        LocalTime parsedClose = parseCloseHour(close);

        verifyOpenBeforeClose(parsedOpen, parsedClose);
        verifyTimeIntervalAtLeastOne(parsedOpen, parsedClose);

        return new RestaurantHours(parsedOpen, parsedClose);
    }

    private LocalTime parseOpenHour(String open) {
        try {
            return LocalTime.parse(open, dateTimeFormatter);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Restaurant open time format is not valid (HH:mm:ss)");
        }
    }

    private LocalTime parseCloseHour(String close) {
        try {
            return LocalTime.parse(close, dateTimeFormatter);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException(
                    "Restaurant close time format is not valid (HH:mm:ss)");
        }
    }

    private void verifyOpenBeforeClose(LocalTime open, LocalTime close) {
        if (close.isBefore(open))
            throw new IllegalArgumentException("Restaurant opening hours are incoherent");
    }

    private void verifyTimeIntervalAtLeastOne(LocalTime open, LocalTime close) {
        if (Duration.between(open, close).toHours() < 1) {
            throw new IllegalArgumentException("Restaurant must be open at least 1 hour");
        }
    }
}
