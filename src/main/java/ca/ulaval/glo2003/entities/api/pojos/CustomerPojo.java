package ca.ulaval.glo2003.entities.api.pojos;

import jakarta.validation.constraints.NotNull;

public class CustomerPojo {
    @NotNull(message = "Customer name must be provided") public String name;

    @NotNull(message = "Customer email must be provided") public String email;

    @NotNull(message = "Customer phone number must be provided") public String phoneNumber;

    public CustomerPojo() {}

    public CustomerPojo(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
