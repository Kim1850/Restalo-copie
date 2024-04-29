package ca.ulaval.glo2003.data;

import ca.ulaval.glo2003.data.inmemory.RestaurantRepositoryInMemory;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.google.common.truth.Truth.assertThat;

class RestaurantRepositoryTest {
    RestaurantRepositoryInMemory repository;

    Restaurant restaurant1, restaurant2;
    String restaurantId;
    private static final String OWNER_ID = "owner 1";

    List<Restaurant> restaurants;

    @BeforeEach
    void setup() {
        repository = new RestaurantRepositoryInMemory();
        restaurant1 = new RestaurantFixture().withOwnerId(OWNER_ID).create();
        restaurant2 =
                new RestaurantFixture()
                        .withName("Mc Donald's")
                        .withRestaurantHours(
                                LocalTime.parse("05:00:00"), LocalTime.parse("10:00:00"))
                        .create();
        restaurantId = restaurant1.getId();
    }

    @Test
    public void givenNonEmptyRepository_whenGet_thenReturnsRestaurant() {
        repository.add(restaurant1);

        Optional<Restaurant> gottenRestaurant = repository.get(restaurantId);

        assertThat(gottenRestaurant.isPresent()).isTrue();
        assertThat(gottenRestaurant.get()).isEqualTo(restaurant1);
    }

    @Test
    public void givenEmptyRepository_whenGet_thenReturnsNull() {
        Optional<Restaurant> restaurant = repository.get(restaurantId);

        assertThat(restaurant.isEmpty()).isTrue();
    }

    @Test
    public void givenExistingOwnerId_whenGetByOwnerId_thenReturnsRestaurantsWithSameOwnerId() {
        repository.add(restaurant1);
        repository.add(restaurant2);

        List<Restaurant> gottenRestaurants = repository.getByOwnerId(OWNER_ID);

        assertThat(gottenRestaurants).containsExactly(restaurant1);
    }

    @Test
    public void givenNonexistentOwnerId_whenGetByOwnerId_thenReturnsEmptyList() {
        repository.add(restaurant2);

        List<Restaurant> gottenRestaurants = repository.getByOwnerId(OWNER_ID);

        assertThat(gottenRestaurants).isEqualTo(List.of());
    }

    @Test
    public void givenDefaultSearch_whenSearch_thenReturnsAllRestaurantsInRepository() {
        repository.add(restaurant1);
        repository.add(restaurant2);
        Search search = new Search(null, new SearchOpened(null, null));

        List<Restaurant> gottenRestaurants = repository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(restaurant1, restaurant2);
    }

    @Test
    public void givenNoMatchSearch_whenSearch_thenReturnsEmptyList() {
        repository.add(restaurant1);
        repository.add(restaurant2);
        Search search =
                new Search(
                        null,
                        new SearchOpened(LocalTime.parse("06:00:00"), LocalTime.parse("18:00:00")));

        List<Restaurant> gottenRestaurants = repository.searchRestaurants(search);

        assertThat(gottenRestaurants).isEqualTo(List.of());
    }

    @Test
    public void whenSearchNameIsSpecific_thenReturnsOnlyRestaurantsWithSpecificName() {
        repository.add(restaurant1);
        repository.add(restaurant2);
        Search search = new Search("mCDo", new SearchOpened(null, null));

        List<Restaurant> gottenRestaurants = repository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(restaurant2);
    }

    @Test
    public void whenSearchNameIsEmpty_thenReturnsAllRestaurantsInRepository() {
        repository.add(restaurant1);
        repository.add(restaurant2);
        Search search = new Search("", new SearchOpened(null, null));

        List<Restaurant> gottenRestaurants = repository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(restaurant1, restaurant2);
    }

    @Test
    public void whenSearchOpenedFromIs10_thenReturnsRestaurantsThatOpenOrAreOpenedAt10() {
        repository.add(restaurant1);
        repository.add(restaurant2);
        Search search = new Search(null, new SearchOpened(LocalTime.parse("10:00:00"), null));

        List<Restaurant> gottenRestaurants = repository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(restaurant1);
    }

    @Test
    public void whenSearchOpenedToIs10_thenReturnsRestaurantsThatCloseOrAreOpenedAt10() {
        repository.add(restaurant1);
        repository.add(restaurant2);
        Search search = new Search(null, new SearchOpened(null, LocalTime.parse("10:00:00")));

        List<Restaurant> gottenRestaurants = repository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(restaurant2);
    }

    @Test
    public void
            whenSearchOpenedFromIs12AndOpenedToIs20_thenReturnsRestaurantsOpenedBetween12And20() {
        repository.add(restaurant1);
        repository.add(restaurant2);
        Search search =
                new Search(
                        null,
                        new SearchOpened(LocalTime.parse("12:00:00"), LocalTime.parse("20:00:00")));

        List<Restaurant> gottenRestaurants = repository.searchRestaurants(search);

        assertThat(gottenRestaurants).containsExactly(restaurant1);
    }
}
