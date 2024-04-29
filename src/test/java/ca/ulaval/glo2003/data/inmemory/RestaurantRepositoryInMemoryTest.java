package ca.ulaval.glo2003.data.inmemory;

import ca.ulaval.glo2003.domain.RestaurantRepository;
import ca.ulaval.glo2003.domain.RestaurantRepositoryTest;

class RestaurantRepositoryInMemoryTest extends RestaurantRepositoryTest {

    @Override
    protected RestaurantRepository createRepository() {
        return new RestaurantRepositoryInMemory();
    }
}
