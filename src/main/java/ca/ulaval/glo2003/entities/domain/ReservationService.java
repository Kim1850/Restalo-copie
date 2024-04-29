package ca.ulaval.glo2003.entities.domain;

import ca.ulaval.glo2003.api.pojos.CustomerPojo;
import ca.ulaval.glo2003.domain.entities.Customer;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.factories.CustomerFactory;
import ca.ulaval.glo2003.domain.factories.ReservationFactory;
import jakarta.ws.rs.NotFoundException;
import org.glassfish.grizzly.utils.Pair;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final ReservationFactory reservationFactory;
    private final CustomerFactory customerFactory;

    public ReservationService(
            ReservationRepository reservationRepository,
            RestaurantRepository restaurantRepository,
            ReservationFactory reservationFactory,
            CustomerFactory customerFactory) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.reservationFactory = reservationFactory;
        this.customerFactory = customerFactory;
    }

    public String createReservation(
            String restaurantId,
            String date,
            String startTime,
            Integer groupSize,
            CustomerPojo customerRequest) {
        Restaurant restaurant =
                restaurantRepository
                        .get(restaurantId)
                        .orElseThrow(() -> new NotFoundException("Restaurant does not exist"));
        Customer customer =
                customerFactory.create(
                        customerRequest.name, customerRequest.email, customerRequest.phoneNumber);
        Map<LocalDateTime, Integer> availabilities =
                reservationRepository.searchAvailabilities(restaurant, parseDate(date));
        Reservation reservation =
                reservationFactory.create(
                        date, startTime, groupSize, customer, restaurant, availabilities);

        reservationRepository.add(reservation);

        return reservation.getNumber();
    }

    private LocalDate parseDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Date format is not valid (YYYY-MM-DD)");
        }
    }

    public Pair<Reservation, Restaurant> getReservation(String number) {
        Reservation reservation =
                reservationRepository
                        .get(number)
                        .orElseThrow(() -> new NotFoundException("Reservation does not exist"));
        Restaurant restaurant =
                restaurantRepository.get(reservation.getRestaurantId()).orElse(null);

        return new Pair<>(reservation, restaurant);
    }

    public void deleteReservation(String number) {
        reservationRepository
                .delete(number)
                .orElseThrow(() -> new NotFoundException("Reservation does not exist"));
    }
}
