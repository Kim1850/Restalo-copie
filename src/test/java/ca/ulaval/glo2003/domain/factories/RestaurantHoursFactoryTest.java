package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.RestaurantHours;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RestaurantHoursFactoryTest {
    RestaurantHoursFactory restaurantHoursFactory;

    @BeforeEach
    void setUp() {
        restaurantHoursFactory = new RestaurantHoursFactory();
    }

    @Test
    void canCreateRestaurantHours() {
        RestaurantHours restaurantHours = restaurantHoursFactory.create(OPEN_HOUR, CLOSE_HOUR);

        assertThat(restaurantHours.getOpen()).isEqualTo(LocalTime.parse(OPEN_HOUR));
        assertThat(restaurantHours.getClose()).isEqualTo(LocalTime.parse(CLOSE_HOUR));
    }

    @Test
    void whenOpenHourFormatIsInvalid_thenThrowsIllegalArgumentException() {
        String invalidOpenHour = "25:25:25";

        assertThrowsWhenCreate(invalidOpenHour, CLOSE_HOUR);
    }

    @Test
    public void whenCloseHourFormatIsInvalid_thenThrowsIllegalArgumentException() {
        String invalidCloseHour = "34-35-76";

        assertThrowsWhenCreate(OPEN_HOUR, invalidCloseHour);
    }

    @Test
    public void whenCloseHourIsBeforeOpenHour_thenThrowsIllegalArgumentException() {
        assertThrowsWhenCreate(CLOSE_HOUR, OPEN_HOUR);
    }

    @Test
    public void whenOpeningTimeIsLessThanAnHour_thenThrowsIllegalArgumentException() {
        String tooSoonCloseHour = "10:30:00";

        assertThrowsWhenCreate(OPEN_HOUR, tooSoonCloseHour);
    }

    private void assertThrowsWhenCreate(String open, String close) {
        assertThrows(
                IllegalArgumentException.class, () -> restaurantHoursFactory.create(open, close));
    }

    private static final String OPEN_HOUR = "10:00:00";
    private static final String CLOSE_HOUR = "21:00:00";
}
