package ca.ulaval.glo2003.domain.entities;

public class CustomerFixture {
    private String name = "Johnny Cash";
    private String email = "johnny.cash@example.com";
    private String phoneNumber = "1234567890";

    public Customer create() {
        return new Customer(name, email, phoneNumber);
    }

    public CustomerFixture withName(String name) {
        this.name = name;
        return this;
    }

    public CustomerFixture withEmail(String email) {
        this.email = email;
        return this;
    }

    public CustomerFixture withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }
}
