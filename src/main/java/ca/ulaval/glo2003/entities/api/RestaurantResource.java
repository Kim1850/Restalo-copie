package ca.ulaval.glo2003.entities.api;

import ca.ulaval.glo2003.api.mappers.OwnerRestaurantResponseMapper;
import ca.ulaval.glo2003.api.requests.CreateRestaurantRequest;
import ca.ulaval.glo2003.api.responses.OwnerRestaurantResponse;
import ca.ulaval.glo2003.domain.RestaurantService;
import ca.ulaval.glo2003.domain.entities.Restaurant;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Path("")
public class RestaurantResource {

    private final RestaurantService restaurantService;
    private final OwnerRestaurantResponseMapper restaurantResponseMapper;

    public RestaurantResource(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
        restaurantResponseMapper = new OwnerRestaurantResponseMapper();
    }

    @GET
    @Path("restaurants/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRestaurant(
            @PathParam("id") String restaurantId, @HeaderParam("Owner") String ownerId) {
        Objects.requireNonNull(ownerId, "Owner id must be provided");

        Restaurant restaurant = restaurantService.getRestaurant(restaurantId, ownerId);
        OwnerRestaurantResponse restaurantResponse = restaurantResponseMapper.from(restaurant);

        return Response.status(Response.Status.OK).entity(restaurantResponse).build();
    }

    @Path("restaurants")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createRestaurant(
            @Context UriInfo uriInfo,
            @HeaderParam("Owner") String ownerId,
            @Valid CreateRestaurantRequest restaurantRequest) {
        Objects.requireNonNull(ownerId, "Owner id must be provided");

        String restaurantId =
                restaurantService.createRestaurant(
                        ownerId,
                        restaurantRequest.name,
                        restaurantRequest.capacity,
                        restaurantRequest.hours,
                        restaurantRequest.reservations);

        return Response.status(Response.Status.CREATED)
                .header(
                        "Location",
                        String.format("%srestaurants/%s", uriInfo.getBaseUri(), restaurantId))
                .build();
    }

    @GET
    @Path("restaurants")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listRestaurants(@HeaderParam("Owner") String ownerId) {
        Objects.requireNonNull(ownerId, "Owner id must be provided");

        List<Restaurant> restaurants = restaurantService.listRestaurants(ownerId);

        return Response.status(Response.Status.OK)
                .entity(
                        restaurants.stream()
                                .map(restaurantResponseMapper::from)
                                .collect(Collectors.toList()))
                .build();
    }

    @DELETE
    @Path("restaurants/{id}")
    public Response deleteRestaurant(
            @PathParam("id") String restaurantId, @HeaderParam("Owner") String ownerId) {
        Objects.requireNonNull(ownerId, "Owner id must be provided");

        restaurantService.deleteRestaurant(restaurantId, ownerId);

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
