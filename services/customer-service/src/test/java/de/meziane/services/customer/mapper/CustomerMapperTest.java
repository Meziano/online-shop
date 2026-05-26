package de.meziane.services.customer.mapper;

import de.meziane.common.model.ContactCategory;
import de.meziane.common.model.CustomerGender;
import de.meziane.services.customer.api.dto.CustomerContactRequest;
import de.meziane.services.customer.api.dto.CustomerRequest;
import de.meziane.services.customer.persistence.entity.Customer;
import de.meziane.services.customer.persistence.entity.CustomerContact;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class CustomerMapperTest {

    private CustomerMapper mapper;

    @Test
    void mapsCreateCustomerRequestToCustomerEntity(){
        var request = generateCustomerRequest();
        var entity = CustomerMapper.toCustomer(request);
        assertThat(entity.getGivenName()).isEqualTo(request.givenName());
        assertThat(entity.getContacts()).hasSize(1);
    }

    @Test
    void mapsCustomerEntityToCustomerResponse() {
        var entity = generateCustomerEntity();
        var response = CustomerMapper.toResponse(entity);
        assertThat(response.givenName()).isEqualTo(entity.getGivenName());
        assertThat(response.contacts())
                .extracting(contact-> contact.category())
                .contains(ContactCategory.EMAIL);
        assertThat(response.contacts())
                .filteredOn(contact -> contact.category() == ContactCategory.PHONE)
                .isNotEmpty();
    }

    @Test
    void returnsNullWhenSourceIsNull() {
        var response = CustomerMapper.toResponse(null);
        assertThat(response).isNull();
    }

    @Test
    void mapsCustomerEntityWithNullDob() {
        var entity = generateCustomerEntityWithoutDob();
        var response = CustomerMapper.toResponse(entity);

        assertThat(response.dob()).isNull();
    }

    private CustomerRequest generateCustomerRequest() {
        return new CustomerRequest(
                "Max",
                "Mustermann",
                LocalDate.of(1990, 1, 1),
                CustomerGender.MALE,
                List.of( new CustomerContactRequest(ContactCategory.EMAIL, "max.Mustermann@test.com"))
        );
    }

    private Customer generateCustomerEntity() {
        var customer = new Customer(  "Max",
                "Mustermann",
                LocalDate.of(1990, 1, 1),
                CustomerGender.MALE);
        customer.addContact(new CustomerContact(ContactCategory.EMAIL, "max.Mustermann@test.com"));
        customer.addContact(new CustomerContact(ContactCategory.PHONE, "+491764124612"));
        return customer;
    }

    private Customer generateCustomerEntityWithoutDob() {
        var customer = new Customer(  "Max",
                "Mustermann",
                null,
                CustomerGender.MALE);
        customer.addContact(new CustomerContact(ContactCategory.EMAIL, "max.Mustermann@test.com"));
        customer.addContact(new CustomerContact(ContactCategory.PHONE, "+491764124612"));
        return customer;
    }
}
