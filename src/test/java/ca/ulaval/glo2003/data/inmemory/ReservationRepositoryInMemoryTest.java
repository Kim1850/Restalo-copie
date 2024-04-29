package ca.ulaval.glo2003.data.inmemory;

import ca.ulaval.glo2003.domain.ReservationRepository;
import ca.ulaval.glo2003.domain.ReservationRepositoryTest;

class ReservationRepositoryInMemoryTest extends ReservationRepositoryTest {
    @Override
    protected ReservationRepository createRepository() {
        return new ReservationRepositoryInMemory();
    }
}
