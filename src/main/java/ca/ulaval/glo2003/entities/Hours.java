package ca.ulaval.glo2003.entities;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Hours {
    private LocalTime open;
    private LocalTime close;

    public Hours(String open, String close) {
        setOpen(open);
        setClose(close);
        validate();
    }

    public void setOpen(String open) {
        if (open == null) throw new NullPointerException("Open time must be provided");
        try {
            this.open = LocalTime.parse(open, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Open time format is not valid (HH:mm:ss)");
        }
    }

    public void setClose(String close) {
        if (close == null) throw new NullPointerException("Close time must be provided");
        try {
            this.close = LocalTime.parse(close, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Close time format is not valid (HH:mm:ss)");
        }
    }

    private void validate() {
        if (close.isBefore(open)) throw new IllegalArgumentException("Incoherent opening hours");
        if (Duration.between(open, close).toHours() < 1) {
            throw new IllegalArgumentException("Restaurant must be open at least 1 hour");
        }
    }

    public LocalTime getOpen() {
        return open;
    }

    public LocalTime getClose() {
        return close;
    }
}
