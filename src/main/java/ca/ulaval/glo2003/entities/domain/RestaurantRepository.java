package ca.ulaval.glo2003.entities.domain;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.Search;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository {

    void add(Restaurant restaurant);

    Optional<Restaurant> get(String restaurantId);

    List<Restaurant> getByOwnerId(String ownerId);

    Optional<Restaurant> delete(String restaurantId);

    List<Restaurant> searchRestaurants(Search search);
}
