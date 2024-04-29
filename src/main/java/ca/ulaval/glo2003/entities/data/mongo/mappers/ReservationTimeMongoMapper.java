package ca.ulaval.glo2003.entities.data.mongo.mappers;

import ca.ulaval.glo2003.data.mongo.entities.ReservationTimeMongo;
import ca.ulaval.glo2003.domain.entities.ReservationTime;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReservationTimeMongoMapper {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;

    public ReservationTimeMongo toMongo(ReservationTime reservationTime) {
        String start = reservationTime.getStart().format(dateTimeFormatter);
        String end = reservationTime.getEnd().format(dateTimeFormatter);
        return new ReservationTimeMongo(start, end);
    }

    public ReservationTime fromMongo(ReservationTimeMongo reservationTime) {
        LocalTime start = LocalTime.parse(reservationTime.start);
        LocalTime end = LocalTime.parse(reservationTime.end);
        Duration duration = Duration.between(start, end);
        int durationMinutes = (int) duration.toMinutes();
        return new ReservationTime(start, durationMinutes);
    }
}
