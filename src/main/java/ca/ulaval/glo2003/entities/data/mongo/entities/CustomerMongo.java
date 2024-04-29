package ca.ulaval.glo2003.entities.data.mongo.entities;

import dev.morphia.annotations.Entity;

@Entity
public class CustomerMongo {
    public String name;
    public String email;
    public String phoneNumber;

    public CustomerMongo() {}

    public CustomerMongo(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
