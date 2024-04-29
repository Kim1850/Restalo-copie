package ca.ulaval.glo2003.entities.data.mongo.mappers;

import ca.ulaval.glo2003.data.mongo.entities.CustomerMongo;
import ca.ulaval.glo2003.domain.entities.Customer;

public class CustomerMongoMapper {

    public CustomerMongo toMongo(Customer customer) {
        return new CustomerMongo(
                customer.getName(), customer.getEmail(), customer.getPhoneNumber());
    }

    public Customer fromMongo(CustomerMongo customer) {
        return new Customer(customer.name, customer.email, customer.phoneNumber);
    }
}
