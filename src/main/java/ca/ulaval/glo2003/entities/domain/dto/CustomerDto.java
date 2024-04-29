package ca.ulaval.glo2003.entities.domain.dto;

import jakarta.validation.constraints.NotNull;

import java.util.Objects;

public class CustomerDto {
    @NotNull(message = "Customer name must be provided") public String name;

    @NotNull(message = "Customer email must be provided") public String email;

    @NotNull(message = "Customer phone number must be provided") public String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        return Objects.equals(name, that.name)
                && Objects.equals(email, that.email)
                && Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, phoneNumber);
    }
}
