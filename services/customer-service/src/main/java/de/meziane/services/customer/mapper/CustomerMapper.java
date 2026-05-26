package de.meziane.services.customer.mapper;

import de.meziane.services.customer.api.dto.CustomerContactRequest;
import de.meziane.services.customer.api.dto.CustomerContactResponse;
import de.meziane.services.customer.api.dto.CustomerRequest;
import de.meziane.services.customer.api.dto.CustomerResponse;
import de.meziane.services.customer.persistence.entity.Customer;
import de.meziane.services.customer.persistence.entity.CustomerContact;

import java.util.List;

public final class CustomerMapper {

    public static CustomerResponse toResponse(Customer customer) {

        if (customer == null) {
            return null;
        }
        return new CustomerResponse(
                customer.getId(),
                customer.getGivenName(),
                customer.getFamilyName(),
                customer.getDob(),
                customer.getGender(),
                customer.getContacts().stream()
                        .map(CustomerMapper::toContactResponse)
                        .toList()
        );
    }
    public static Customer toCustomer(CustomerRequest custRequest){
        Customer customer = new Customer(
                custRequest.givenName(),
                custRequest.familyName(),
                custRequest.dob(),
                custRequest.gender()
        );
        custRequest.contacts()
                .stream()
                .map(CustomerMapper::toContactEntity)
                .forEach(customer::addContact);
        return customer;
    }

    private static CustomerContact toContactEntity(CustomerContactRequest request) {
        return new CustomerContact(
                request.category(),
                request.value()
        );
    }

    private static CustomerContactResponse toContactResponse(CustomerContact contact) {
        return new CustomerContactResponse(
                contact.getCategory(),
                contact.getValue()
        );
    }
}
