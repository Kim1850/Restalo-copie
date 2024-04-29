package ca.ulaval.glo2003.entities.data.inmemory;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.Search;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class RestaurantRepositoryInMemory implements RestaurantRepository {

    private final Map<String, Restaurant> restaurantIdToRestaurant;

    public RestaurantRepositoryInMemory() {
        restaurantIdToRestaurant = new HashMap<>();
    }

    @Override
    public void add(Restaurant restaurant) {
        restaurantIdToRestaurant.put(restaurant.getId(), restaurant);
    }

    @Override
    public Optional<Restaurant> get(String restaurantId) {
        return Optional.ofNullable(restaurantIdToRestaurant.get(restaurantId));
    }

    @Override
    public List<Restaurant> getByOwnerId(String ownerId) {
        return restaurantIdToRestaurant.values().stream()
                .filter(restaurant -> restaurant.getOwnerId().equals(ownerId))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Restaurant> delete(String restaurantId) {
        return Optional.ofNullable(restaurantIdToRestaurant.remove(restaurantId));
    }

    @Override
    public List<Restaurant> searchRestaurants(Search search) {
        return restaurantIdToRestaurant.values().stream()
                .filter(restaurant -> matchesRestaurantName(restaurant, search.getName()))
                .filter(
                        restaurant ->
                                matchesRestaurantOpenHour(
                                        restaurant.getHours(), search.getSearchOpened().getFrom()))
                .filter(
                        restaurant ->
                                matchesRestaurantCloseHour(
                                        restaurant.getHours(), search.getSearchOpened().getTo()))
                .collect(Collectors.toList());
    }

    private boolean matchesRestaurantName(Restaurant restaurant, String name) {
        if (Objects.isNull(name)) return true;
        return restaurant
                .getName()
                .toLowerCase()
                .replaceAll("\\s", "")
                .contains(name.toLowerCase().replaceAll("\\s", ""));
    }

    private boolean matchesRestaurantOpenHour(RestaurantHours restaurantHours, LocalTime from) {
        if (Objects.isNull(from)) return true;
        return !from.isBefore(restaurantHours.getOpen())
                && from.isBefore(restaurantHours.getClose());
    }

    private boolean matchesRestaurantCloseHour(RestaurantHours restaurantHours, LocalTime to) {
        if (Objects.isNull(to)) return true;
        return !to.isAfter(restaurantHours.getClose()) && to.isAfter(restaurantHours.getOpen());
    }
}
