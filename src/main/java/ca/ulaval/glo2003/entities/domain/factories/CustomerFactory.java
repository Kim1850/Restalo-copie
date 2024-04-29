package ca.ulaval.glo2003.entities.domain.factories;

import ca.ulaval.glo2003.domain.entities.Customer;

import java.util.regex.Pattern;

public class CustomerFactory {
    public Customer create(String name, String email, String phoneNumber) {
        verifyNameNotEmpty(name);
        verifyEmailFormat(email);
        verifyPhoneNumberFormat(phoneNumber);

        return new Customer(name, email, phoneNumber);
    }

    private void verifyNameNotEmpty(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Customer name must not be empty");
        }
    }

    private void verifyEmailFormat(String email) {
        if (!Pattern.compile("^[A-Za-z0-9_.+-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9-.]+$")
                .matcher(email)
                .matches()) {
            throw new IllegalArgumentException("Customer email must be valid (x@y.z)");
        }
    }

    private void verifyPhoneNumberFormat(String phoneNumber) {
        if (!Pattern.compile("\\d{10}").matcher(phoneNumber).matches()) {
            throw new IllegalArgumentException("Customer phone number must be 10 numbers");
        }
    }
}
