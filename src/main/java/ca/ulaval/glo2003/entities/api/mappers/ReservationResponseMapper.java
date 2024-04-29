package ca.ulaval.glo2003.entities.api.mappers;

import ca.ulaval.glo2003.api.pojos.CustomerPojo;
import ca.ulaval.glo2003.api.pojos.ReservationTimePojo;
import ca.ulaval.glo2003.api.responses.ReservationResponse;
import ca.ulaval.glo2003.api.responses.UserRestaurantResponse;
import ca.ulaval.glo2003.domain.entities.Reservation;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import org.glassfish.grizzly.utils.Pair;

import java.time.format.DateTimeFormatter;

public class ReservationResponseMapper {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

    public ReservationResponse from(Pair<Reservation, Restaurant> pair) {
        Reservation reservation = pair.getFirst();
        Restaurant restaurant = pair.getSecond();
        CustomerPojo customer =
                new CustomerPojo(
                        reservation.getCustomer().getName(),
                        reservation.getCustomer().getEmail(),
                        reservation.getCustomer().getPhoneNumber());
        ReservationTimePojo reservationTime =
                new ReservationTimePojo(
                        reservation.getReservationTime().getStart().format(timeFormatter),
                        reservation.getReservationTime().getEnd().format(timeFormatter));
        UserRestaurantResponse userRestaurant = new UserRestaurantResponseMapper().from(restaurant);

        return new ReservationResponse(
                reservation.getNumber(),
                reservation.getDate().format(dateFormatter),
                reservationTime,
                reservation.getGroupSize(),
                customer,
                userRestaurant);
    }
}
