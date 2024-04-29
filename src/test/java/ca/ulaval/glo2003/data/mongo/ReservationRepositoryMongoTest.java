package ca.ulaval.glo2003.data.mongo;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.ReservationRepositoryTest;
import com.mongodb.client.MongoClients;
import dev.morphia.Morphia;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
class ReservationRepositoryMongoTest extends ReservationRepositoryTest {

    @Container private final MongoDBContainer mongoContainer = new MongoDBContainer("mongo:7.0");

    @Override
    protected ReservationRepository createRepository() {
        var mongoUrl = MongoClients.create(mongoContainer.getConnectionString());
        var datastore = Morphia.createDatastore(mongoUrl, "tests");
        return new ReservationRepositoryMongo(datastore);
    }
}
