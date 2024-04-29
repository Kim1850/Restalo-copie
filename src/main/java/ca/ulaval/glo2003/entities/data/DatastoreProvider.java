package ca.ulaval.glo2003.entities.data;

import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class DatastoreProvider {
    public Datastore provide() {
        String mongoUrl = System.getenv("MONGO_CLUSTER_URL");
        String mongoDatabase = System.getenv("MONGO_DATABASE");

        return Morphia.createDatastore(MongoClients.create(mongoUrl), mongoDatabase);
    }
}
