package ca.ulaval.glo2003.entities.data.mongo;

import ca.ulaval.glo2003.data.mongo.entities.RestaurantMongo;
import ca.ulaval.glo2003.data.mongo.mappers.RestaurantMongoMapper;
import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import ca.ulaval.glo2003.domain.entities.Search;
import dev.morphia.Datastore;

import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static dev.morphia.query.filters.Filters.eq;

public class RestaurantRepositoryMongo implements RestaurantRepository {
    private final Datastore datastore;

    private final RestaurantMongoMapper restaurantMongoMapper = new RestaurantMongoMapper();

    public RestaurantRepositoryMongo(Datastore datastore) {
        this.datastore = datastore;
    }

    @Override
    public void add(Restaurant restaurant) {
        datastore.save(restaurantMongoMapper.toMongo(restaurant));
    }

    @Override
    public Optional<Restaurant> get(String restaurantId) {
        return datastore.find(RestaurantMongo.class).stream()
                .filter(restaurantMongo -> Objects.equals(restaurantMongo.id, restaurantId))
                .findFirst()
                .map(restaurantMongoMapper::fromMongo);
    }

    @Override
    public List<Restaurant> getByOwnerId(String ownerId) {
        return datastore.find(RestaurantMongo.class).stream()
                .filter(restaurantMongo -> Objects.equals(restaurantMongo.ownerId, ownerId))
                .map(restaurantMongoMapper::fromMongo)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Restaurant> delete(String restaurantId) {
        Optional<Restaurant> restaurant = get(restaurantId);
        datastore.find(RestaurantMongo.class).filter(eq("_id", restaurantId)).delete();
        return restaurant;
    }

    @Override
    public List<Restaurant> searchRestaurants(Search search) {
        return datastore.find(RestaurantMongo.class).stream()
                .filter(
                        restaurant ->
                                matchesRestaurantName(
                                        restaurantMongoMapper.fromMongo(restaurant),
                                        search.getName()))
                .filter(
                        restaurant ->
                                matchesRestaurantOpenHour(
                                        restaurantMongoMapper.fromMongo(restaurant).getHours(),
                                        search.getSearchOpened().getFrom()))
                .filter(
                        restaurant ->
                                matchesRestaurantCloseHour(
                                        restaurantMongoMapper.fromMongo(restaurant).getHours(),
                                        search.getSearchOpened().getTo()))
                .map(restaurantMongoMapper::fromMongo)
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
