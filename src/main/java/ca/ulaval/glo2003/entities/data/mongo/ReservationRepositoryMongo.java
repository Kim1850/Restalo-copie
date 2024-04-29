package ca.ulaval.glo2003.entities.data.mongo;

import ca.ulaval.glo2003.data.mongo.entities.ReservationMongo;
import ca.ulaval.glo2003.data.mongo.mappers.ReservationMongoMapper;
import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import dev.morphia.Datastore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static dev.morphia.query.filters.Filters.eq;

public class ReservationRepositoryMongo implements ReservationRepository {
    private final Datastore datastore;

    private final ReservationMongoMapper reservationMongoMapper = new ReservationMongoMapper();

    public ReservationRepositoryMongo(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void add(Reservation reservation) {
        datastore.save(reservationMongoMapper.toMongo(reservation));
    }

    @Override
    public Optional<Reservation> get(String reservationId) {
        return datastore.find(ReservationMongo.class).stream()
                .filter(reservationMongo -> Objects.equals(reservationMongo.number, reservationId))
                .findFirst()
                .map(reservationMongoMapper::fromMongo);
    }

    @Override
    public Optional<Reservation> delete(String reservationId) {
        Optional<Reservation> reservation = get(reservationId);
        datastore.find(ReservationMongo.class).filter(eq("_id", reservationId)).delete();
        return reservation;
    }

    @Override
    public List<Reservation> deleteAll(String restaurantId) {
        List<Reservation> reservations = searchReservations(restaurantId, null, null);
        for (Reservation reservation : reservations) {
            delete(reservation.getNumber());
        }
        return reservations;
    }

    @Override
    public List<Reservation> searchReservations(
            String restaurantId, LocalDate date, String customerName) {

        return datastore.find(ReservationMongo.class).stream()
                .filter(reservation -> reservation.restaurantId.equals(restaurantId))
                .filter(
                        reservation ->
                                matchesCustomerName(
                                        reservationMongoMapper.fromMongo(reservation).getCustomer(),
                                        customerName))
                .filter(
                        reservation ->
                                matchesDate(
                                        reservationMongoMapper.fromMongo(reservation).getDate(),
                                        date))
                .map(reservationMongoMapper::fromMongo)
                .toList();
    }

    private boolean matchesCustomerName(Customer customer, String customerName) {
        if (Objects.isNull(customerName)) return true;
        return customer.getName()
                .toLowerCase()
                .replaceAll("\\s", "")
                .startsWith(customerName.toLowerCase().replaceAll("\\s", ""));
    }

    private boolean matchesDate(LocalDate reservationDate, LocalDate date) {
        if (Objects.isNull(date)) return true;
        return reservationDate.equals(date);
    }

    @Override
    public Map<LocalDateTime, Integer> searchAvailabilities(Restaurant restaurant, LocalDate date) {
        List<Reservation> reservations = searchReservations(restaurant.getId(), date, null);
        List<LocalDateTime> intervals =
                create15MinutesIntervals(
                        date,
                        roundToNext15Minutes(restaurant.getHours().getOpen()),
                        calculateRoundedCloseTime(restaurant));
        Map<LocalDateTime, Integer> availabilities = new LinkedHashMap<>();

        intervals.forEach(dateTime -> availabilities.put(dateTime, restaurant.getCapacity()));
        updateAvailabilitiesWithReservations(availabilities, reservations);

        return availabilities;
    }

    private List<LocalDateTime> create15MinutesIntervals(
            LocalDate date, LocalTime start, LocalTime end) {
        List<LocalDateTime> localDateTimes = new ArrayList<>();
        for (LocalTime current = start; current.isBefore(end); current = current.plusMinutes(15)) {
            localDateTimes.add(LocalDateTime.of(date, current));
        }
        return localDateTimes;
    }

    private LocalTime roundToNext15Minutes(LocalTime time) {
        if (time.getNano() != 0) {
            time = time.plusSeconds(1);
        }
        return time.withNano(0).plusSeconds((4500 - (time.toSecondOfDay() % 3600)) % 900);
    }

    private LocalTime calculateRoundedCloseTime(Restaurant restaurant) {
        return roundToNext15Minutes(
                restaurant
                        .getHours()
                        .getClose()
                        .plusSeconds(1)
                        .minusMinutes(restaurant.getConfiguration().getDuration()));
    }

    private void updateAvailabilitiesWithReservations(
            Map<LocalDateTime, Integer> availabilities, List<Reservation> reservations) {
        for (Reservation reservation : reservations) {
            List<LocalDateTime> reservationInterval =
                    create15MinutesIntervals(
                            reservation.getDate(),
                            reservation.getReservationTime().getStart(),
                            reservation.getReservationTime().getEnd());
            reservationInterval.forEach(
                    dateTime ->
                            availabilities.put(
                                    dateTime,
                                    availabilities.get(dateTime) - reservation.getGroupSize()));
        }
    }
}
