package ca.ulaval.glo2003.domain.entities;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RestaurantTestUtils {
    public static List<Restaurant> createRestaurants(int number) {
        return IntStream.range(0, number)
                .mapToObj(
                        i ->
                                new Restaurant(
                                        UUID.randomUUID(),
                                        String.format("owner %d", i),
                                        String.format("restaurant %d", i),
                                        5,
                                        new RestaurantHours(
                                                LocalTime.of(10, 0, 0), LocalTime.of(22, 0, 0)),
                                        new RestaurantConfiguration(75)))
                .collect(Collectors.toList());
    }
}
