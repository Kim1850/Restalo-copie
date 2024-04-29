package ca.ulaval.glo2003.domain.factories;

import ca.ulaval.glo2003.domain.entities.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerFactoryTest {

    CustomerFactory customerFactory;

    @BeforeEach
    void setUp() {
        customerFactory = new CustomerFactory();
    }

    @Test
    void whenValidInputs_thenCustomerCreated() {
        Customer customer = new Customer(NAME, EMAIL, PHONE_NUMBER);

        Customer gottenCustomer = customerFactory.create(NAME, EMAIL, PHONE_NUMBER);

        assertThat(gottenCustomer).isEqualTo(customer);
    }

    @Test
    void whenEmptyName_thenThrowsIllegalArgumentException() {
        String emptyName = "";

        assertThrows(
                IllegalArgumentException.class,
                () -> customerFactory.create(emptyName, EMAIL, PHONE_NUMBER));
    }

    @Test
    void whenInvalidEmail_thenThrowsIllegalArgumentException() {
        String invalidEmail = "invalid_email";

        assertThrows(
                IllegalArgumentException.class,
                () -> customerFactory.create(NAME, invalidEmail, PHONE_NUMBER));
    }

    @Test
    void whenInvalidPhoneNumber_thenThrowsIllegalArgumentException() {
        String invalidPhoneNumber = "123";

        assertThrows(
                IllegalArgumentException.class,
                () -> customerFactory.create(NAME, EMAIL, invalidPhoneNumber));
    }

    private static final String NAME = "Johnny Cash";
    private static final String EMAIL = "johnny.cash@example.com";
    private static final String PHONE_NUMBER = "1234567890";
}
