package ca.ulaval.glo2003.api;

import ca.ulaval.glo2003.api.exceptions.*;
import ca.ulaval.glo2003.api.mappers.OwnerRestaurantResponseMapper;
import ca.ulaval.glo2003.api.requests.CreateRestaurantRequest;
import ca.ulaval.glo2003.api.requests.RestaurantRequestFixture;
import ca.ulaval.glo2003.api.responses.ErrorResponse;
import ca.ulaval.glo2003.api.responses.OwnerRestaurantResponse;
import ca.ulaval.glo2003.api.responses.RestaurantResponseFixture;
import ca.ulaval.glo2003.data.inmemory.ReservationRepositoryInMemory;
import ca.ulaval.glo2003.data.inmemory.RestaurantRepositoryInMemory;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import ca.ulaval.glo2003.domain.entities.RestaurantFixture;
import ca.ulaval.glo2003.domain.factories.RestaurantConfigurationFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantFactory;
import ca.ulaval.glo2003.domain.factories.RestaurantHoursFactory;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.*;
import org.assertj.core.api.Assertions;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantResourceIntegratedTest {

    private static final OwnerRestaurantResponseMapper RESTAURANT_MAPPER =
            new OwnerRestaurantResponseMapper();
    static RestaurantRepositoryInMemory restaurantRepository = new RestaurantRepositoryInMemory();
    static ReservationRepositoryInMemory reservationRepository =
            new ReservationRepositoryInMemory();

    static RestaurantFactory restaurantFactory = new RestaurantFactory();
    static RestaurantHoursFactory restaurantHoursFactory = new RestaurantHoursFactory();
    static RestaurantConfigurationFactory restaurantConfigurationFactory =
            new RestaurantConfigurationFactory();

    private static JerseyTestApi api;

    private static final RestaurantService restaurantService =
            new RestaurantService(
                    restaurantRepository,
                    reservationRepository,
                    restaurantFactory,
                    restaurantHoursFactory,
                    restaurantConfigurationFactory);

    private static final Restaurant RESTAURANT = new RestaurantFixture().create();
    private static final String OWNER_ID = RESTAURANT.getOwnerId();
    private static final OwnerRestaurantResponse restaurantResponse =
            new RestaurantResponseFixture().create(RESTAURANT.getId());

    protected static Application configure() {
        return new ResourceConfig()
                .register(new RestaurantResource(restaurantService))
                .register(new NullPointerExceptionMapper())
                .register(new IllegalArgumentExceptionMapper())
                .register(new ConstraintViolationExceptionMapper())
                .register(new RuntimeExceptionMapper())
                .register(new NotFoundExceptionMapper());
    }

    @BeforeAll
    static void startServer() {
        api = new JerseyTestApi(configure());
        api.start();

        restaurantRepository.add(RESTAURANT);
        restaurantRepository.add(RESTAURANT2);
        restaurantRepository.add(OTHER_RESTAURANT);

        List<OwnerRestaurantResponse> restaurants = new ArrayList<>();

        restaurants.add(RESTAURANT_MAPPER.from(RESTAURANT));
        restaurants.add(RESTAURANT_MAPPER.from(RESTAURANT2));
    }

    @Test
    public void givenValidOwnerId_whenPostRequest_createsRestaurantAndReturns201() {
        Response response =
                api.path("/restaurants")
                        .request()
                        .header("Owner", OWNER_ID)
                        .post(Entity.entity(restaurantRequest, MediaType.APPLICATION_JSON));
        Assertions.assertThat(response.getStatus()).isEqualTo(201);
        String[] headers = response.getHeaderString(HttpHeaders.LOCATION).split("restaurants/", 2);

        Assertions.assertThat(headers[1]).isNotEmpty();
        Assertions.assertThat(headers[1])
                .matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}");
    }

    @Test
    void givenMissingOwnerId_whenPostRequest_Returns400AndMissingParameterBody() {
        Response response =
                api.path("/restaurants")
                        .request()
                        .post(Entity.entity(restaurantRequest, MediaType.APPLICATION_JSON));

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error())
                .isEqualTo(
                        new ErrorResponse("MISSING_PARAMETER", "Owner id must be provided")
                                .error());
    }

    @Test
    void givenInvalidParameter_whenPostRequest_Returns400AndInvalidParameterBody() {
        Response response =
                api.path("/restaurants")
                        .request()
                        .header("Owner", OWNER_ID)
                        .post(Entity.entity(invalidRestaurantRequest, MediaType.APPLICATION_JSON));

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error())
                .isEqualTo(new ErrorResponse("INVALID_PARAMETER", "Invalid Parameter").error());
    }

    @Test
    void givenMissingRestaurantParameter_whenPostRequest_Returns400AndInvalidParameterBody() {
        Response response =
                api.path("/restaurants")
                        .request()
                        .header("Owner", OWNER_ID)
                        .post(Entity.entity(missingRestaurantRequest, MediaType.APPLICATION_JSON));

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error())
                .isEqualTo(new ErrorResponse("MISSING_PARAMETER", "Missing Parameter").error());
    }

    @Test
    void givenValidParameters_whenGetRestaurant_Returns200AndRestaurantBody() {
        Response response =
                api.path("/restaurants/" + RESTAURANT.getId())
                        .request()
                        .header("Owner", OWNER_ID)
                        .get();
        Assertions.assertThat(response.getStatus()).isEqualTo(200);

        OwnerRestaurantResponse receivedRestaurantResponse =
                response.readEntity(OwnerRestaurantResponse.class);
        Assertions.assertThat(receivedRestaurantResponse).isEqualTo(restaurantResponse);
    }

    @Test
    void givenInvalidRestaurantId_whenGetRestaurant_Returns404NotFound() {
        Response response =
                api.path("/restaurants/" + INVALID_RESTAURANT_ID)
                        .request()
                        .header("Owner", OWNER_ID)
                        .get();

        Assertions.assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    void givenInvalidOwnerId_whenGetRestaurant_Returns404NotFound() {
        Response response =
                api.path("/restaurants/" + RESTAURANT.getId())
                        .request()
                        .header("Owner", INVALID_OWNER_ID)
                        .get();

        Assertions.assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    void givenMissingOwner_whenGetRestaurant_Returns400AndMissingParameterBody() {
        Response response = api.path("/restaurants/" + RESTAURANT.getId()).request().get();

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error())
                .isEqualTo(new ErrorResponse("MISSING_PARAMETER", "Missing Parameter").error());
    }

    @Test
    void givenValidParameters_whenGetRestaurantList_Returns200AndOwnerRestaurantList() {
        Response response = api.path("/restaurants/").request().header("Owner", OWNER_ID).get();

        List<OwnerRestaurantResponse> entities = response.readEntity(new GenericType<>() {});

        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(entities.size())
                .isEqualTo(restaurantService.listRestaurants(OWNER_ID).size());
        for (Restaurant expectedRestaurant : restaurantService.listRestaurants(OWNER_ID)) {
            assertThat(entities).contains(RESTAURANT_MAPPER.from(expectedRestaurant));
        }
    }

    @Test
    void givenInvalidOwner_whenGetRestaurantList_ReturnsEmptyRestaurantList() {
        Response response =
                api.path("/restaurants/").request().header("Owner", INVALID_OWNER_ID).get();

        Assertions.assertThat(response.getStatus()).isEqualTo(200);
        Assertions.assertThat(response.readEntity(List.class)).isEqualTo(new ArrayList<>());
    }

    @Test
    void givenMissingOwner_whenGetRestaurantList_Returns400AndMissingParameterBody() {
        Response response = api.path("/restaurants/").request().get();

        Assertions.assertThat(response.getStatus()).isEqualTo(400);
        Assertions.assertThat(response.readEntity(ErrorResponse.class).error())
                .isEqualTo(new ErrorResponse("MISSING_PARAMETER", "Missing Parameter").error());
    }

    private static final String OTHER_OWNER_NAME = "Shaker";
    private static final Restaurant RESTAURANT2 =
            new RestaurantFixture().withName(OTHER_OWNER_NAME).create();

    private static final String OTHER_OWNER_ID = "lola";
    private static final Restaurant OTHER_RESTAURANT =
            new RestaurantFixture().withOwnerId(OTHER_OWNER_ID).create();

    private static final CreateRestaurantRequest restaurantRequest =
            new RestaurantRequestFixture().create();
    private static final CreateRestaurantRequest invalidRestaurantRequest =
            new RestaurantRequestFixture().withInvalidParameter(-30);
    private static final CreateRestaurantRequest missingRestaurantRequest =
            new RestaurantRequestFixture().withMissingParameter();

    private static final String INVALID_OWNER_ID = "ABCD";
    private static final String INVALID_RESTAURANT_ID = "32JFD323";
}
