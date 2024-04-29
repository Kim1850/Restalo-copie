package ca.ulaval.glo2003.data.mongo;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.RestaurantRepositoryTest;
import com.mongodb.client.MongoClients;
import dev.morphia.Morphia;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class RestaurantRepositoryMongoTest extends RestaurantRepositoryTest {

    @Container private final MongoDBContainer mongoContainer = new MongoDBContainer("mongo:7.0");

    @Override
    protected RestaurantRepository createRepository() {
        var mongoUrl = MongoClients.create(mongoContainer.getConnectionString());
        var datastore = Morphia.createDatastore(mongoUrl, "tests");
        return new RestaurantRepositoryMongo(datastore);
    }
}
