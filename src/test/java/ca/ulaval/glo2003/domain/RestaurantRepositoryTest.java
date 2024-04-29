package ca.ulaval.glo2003.domain;

import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import ca.ulaval.glo2003.domain.entities.Search;
import ca.ulaval.glo2003.domain.entities.SearchOpened;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

public abstract class RestaurantRepositoryTest {

    protected abstract RestaurantRepository createRepository();

    private RestaurantRepository restaurantRepository;

    Restaurant restaurant;
    Restaurant otherRestaurant;

    @BeforeEach
    void setup() {
        restaurantRepository = createRepository();

        restaurant = new RestaurantFixture().withOwnerId(OWNER_ID).withId(RESTAURANT_ID).create();
        otherRestaurant =
                new RestaurantFixture().withName(NAME).withRestaurantHours(OPEN, CLOSE).create();
    }

    @Test
    public void whenGet_thenReturnsRestaurant() {
        restaurantRepository.add(restaurant);

        Optional<Restaurant> gottenRestaurant = restaurantRepository.get(RESTAURANT_ID);

        assertThat(gottenRestaurant.isPresent()).isTrue();
        assertThat(gottenRestaurant.get()).isEqualTo(restaurant);
    }

    @Test
    public void givenNoRestaurantsAdded_whenGet_thenReturnsEmpty() {
        Optional<Restaurant> restaurant = restaurantRepository.get(RESTAURANT_ID);

        assertThat(restaurant.isEmpty()).isTrue();
    }

    @Test
    public void whenGetByOwnerId_thenReturnsRestaurantsWithSameOwnerId() {
        restaurantRepository.add(restaurant);
        restaurantRepository.add(otherRestaurant);

        List<Restaurant> gottenRestaurants = restaurantRepository.getByOwnerId(OWNER_ID);

        assertThat(gottenRestaurants).containsExactly(restaurant);
    }

    @Test
    public void givenNonExistingOwnerId_whenGetByOwnerId_thenReturnsEmptyList() {
        restaurantRepository.add(otherRestaurant);

        List<Restaurant> gottenRestaurants = restaurantRepository.getByOwnerId(OWNER_ID);

        assertThat(gottenRestaurants).isEmpty();
    }

    @Test
    public void whenDelete_thenReturnsDeletedRestaurant() {
        restaurantRepository.add(restaurant);

        Optional<Restaurant> gottenRestaurant = restaurantRepository.delete(RESTAURANT_ID);

        assertThat(restaurantRepository.get(RESTAURANT_ID).isPresent()).isFalse();
        assertThat(gottenRestaurant.isPresent()).isTrue();
        assertThat(gottenRestaurant.get()).isEqualTo(restaurant);
    }

    @Test
    public void givenNoRestaurantsAdded_whenDelete_thenReturnsEmpty() {
        Optional<Restaurant> gottenRestaurant = restaurantRepository.delete(RESTAURANT_ID);

        assertThat(gottenRestaurant.isEmpty()).isTrue();
    }

    @Test
    public void givenEmptySearch_whenSearchRestaurants_thenReturnsAllRestaurantsInRepository() {
        restaurantRepository.add(restaurant);
        restaurantRepository.add(otherRestaurant);
        Search emptySearch = new Search(null, new SearchOpened(null, null));

        List<Restaurant> gottenRestaurants = restaurantRepository.searchRestaurants(emptySearch);

        assertThat(gottenRestaurants).containsExactly(restaurant, otherRestaurant);
    }

    @Test
    public void whenSearchRestaurantsHasNoMatch_thenReturnsEmptyList() {
        restaurantRepository.add(restaurant);
        restaurantRepository.add(otherRestaurant);
        LocalTime noMatchOpen = LocalTime.parse("06:00:00");
        LocalTime noMatchClose = LocalTime.parse("18:00:00");
        Search noMatchSearch = new Search(null, new SearchOpened(noMatchOpen, noMatchClose));

        List<Restaurant> gottenRestaurants = restaurantRepository.searchRestaurants(noMatchSearch);

        assertThat(gottenRestaurants).isEmpty();
    }

    @Test
    public void whenSearchRestaurantsWithName_thenReturnsRestaurantsWithMatchingName() {
        restaurantRepository.add(restaurant);
        restaurantRepository.add(otherRestaurant);
        String name = "mCDo";
        Search search = new Search(name, new SearchOpened(null, null));

        List<Restaurant> gottenRestaurants = restaurantRepository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(otherRestaurant);
    }

    @Test
    public void whenSearchRestaurantsWithEmptyName_thenReturnsAllRestaurantsInRepository() {
        restaurantRepository.add(restaurant);
        restaurantRepository.add(otherRestaurant);
        Search search = new Search("", new SearchOpened(null, null));

        List<Restaurant> gottenRestaurants = restaurantRepository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(restaurant, otherRestaurant);
    }

    @Test
    public void
            givenOpenedFromIs10_whenSearchRestaurants_thenReturnsRestaurantsThatOpenOrAreOpenedAt10() {
        restaurantRepository.add(restaurant);
        restaurantRepository.add(otherRestaurant);
        LocalTime openedFrom = LocalTime.parse("10:00:00");
        Search search = new Search(null, new SearchOpened(openedFrom, null));

        List<Restaurant> gottenRestaurants = restaurantRepository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(restaurant);
    }

    @Test
    public void
            givenOpenedToIs10_whenSearchRestaurants_thenReturnsRestaurantsThatCloseOrAreOpenedAt10() {
        restaurantRepository.add(restaurant);
        restaurantRepository.add(otherRestaurant);
        LocalTime openedTo = LocalTime.parse("10:00:00");
        Search search = new Search(null, new SearchOpened(null, openedTo));

        List<Restaurant> gottenRestaurants = restaurantRepository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(otherRestaurant);
    }

    @Test
    public void
            givenOpenedFrom10AndOpenedTo20_whenSearchRestaurants_thenReturnsRestaurantsOpenedBetween12And20() {
        restaurantRepository.add(restaurant);
        restaurantRepository.add(otherRestaurant);
        LocalTime openedFrom = LocalTime.parse("12:00:00");
        LocalTime openedTo = LocalTime.parse("20:00:00");
        Search search = new Search(null, new SearchOpened(openedFrom, openedTo));

        List<Restaurant> gottenRestaurants = restaurantRepository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(restaurant);
    }

    private static final String OWNER_ID = "owner 1";
    private static final String RESTAURANT_ID = "restaurant id";
    private static final String NAME = "Mc Donald's";
    private static final LocalTime OPEN = LocalTime.parse("05:00:00");
    private static final LocalTime CLOSE = LocalTime.parse("10:00:00");
}
