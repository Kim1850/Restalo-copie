package ca.ulaval.glo2003.domain.entities;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {
    public static List<Restaurant> createRestaurants(int quantity) {
        return IntStream.range(0, quantity)
                .mapToObj(
                        i ->
                                new RestaurantFixture()
                                        .withName(String.format("restaurant %d", i))
                                        .create())
                .collect(Collectors.toList());
    }

    public static List<Reservation> createReservations(int quantity, String restaurantId) {
        return IntStream.range(0, quantity)
                .mapToObj(
                        i ->
                                new ReservationFixture()
                                        .withNumber(Integer.toString(i))
                                        .withRestaurantId(restaurantId)
                                        .create())
                .collect(Collectors.toList());
    }
}
