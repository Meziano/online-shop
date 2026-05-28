package de.meziane.services.customer.service;

import de.meziane.common.model.ContactCategory;
import de.meziane.common.model.CustomerGender;
import de.meziane.services.customer.api.dto.CustomerContactRequest;
import de.meziane.services.customer.api.dto.CustomerRequest;
import de.meziane.services.customer.api.dto.CustomerResponse;
import de.meziane.services.customer.mapper.CustomerMapper;
import de.meziane.services.customer.persistence.entity.Customer;
import de.meziane.services.customer.persistence.entity.CustomerContact;
import de.meziane.services.customer.persistence.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void shouldCreateCustomer() throws Exception {
        /*Customer customer = generateCustomer();
        when(customerRepository.save(customer)).thenReturn(customer);
        CustomerRequest request = generateCustomerRequest();
        CustomerResponse response = customerService.createCustomer(request);
        assertEquals("Max", response.givenName());*/
        /*CustomerRequest request = generateCustomerRequest();
        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CustomerResponse response = customerService.createCustomer(request);

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).save(captor.capture());

        assertEquals("Max", captor.getValue().getGivenName());
        assertEquals("Mustermann", captor.getValue().getFamilyName());*/
        CustomerRequest request = generateCustomerRequest();

        Customer customer = generateCustomer();
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerResponse response = customerService.createCustomer(request);

        assertEquals("Max", response.givenName());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void shouldGetCustomer() throws Exception {
        Customer customer = generateCustomer();
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));
        CustomerResponse response = customerService.getCustomerById(1L);
        assertEquals("Max", response.givenName());
        verify(customerRepository).findById(1L);
    }

    @Test
    void shouldThrowWhenCustomerNotFoundById() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> customerService.getCustomerById(1L));

        verify(customerRepository).findById(1L);
    }

    @Test
    void shouldUpdateCustomer() throws Exception {
        Long id = 1L;
        Customer existingCustomer = generateCustomer();
        when(customerRepository.findById(id)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(any(Customer.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        List<CustomerContactRequest> contacts = existingCustomer.getContacts()
                .stream()
                .map(c -> new CustomerContactRequest(c.getCategory(), c.getValue()))
                .toList();

        CustomerRequest request = new CustomerRequest(
                "Anna",
                "Musterfrau",
                existingCustomer.getDob(),
                existingCustomer.getGender(),
                contacts
        );


        CustomerResponse response = customerService.updateCustomer(id, request);

        assertEquals("Anna", response.givenName());
        assertEquals("Musterfrau", response.familyName());

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(customerRepository).findById(id);
        verify(customerRepository).save(captor.capture());

        Customer savedCustomer = captor.getValue();
        assertEquals("Anna", savedCustomer.getGivenName());
        assertEquals("Musterfrau", savedCustomer.getFamilyName());

    }

    @Test
    void shouldThrowWhenCustomerNotFoundOnUpdate() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());
        CustomerRequest request = generateCustomerRequest();

        assertThrows(ResponseStatusException.class,
                () -> customerService.updateCustomer(1L, request));

        verify(customerRepository).findById(1L);
        verify(customerRepository, never()).save(any());
    }

    @Test
    void shouldDeleteCustomer() throws Exception {
        Customer customer = generateCustomer();
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        customerService.deleteCustomer(customer.getId());
        verify(customerRepository).findById(customer.getId());
        verify(customerRepository).delete(customer);
    }

    @Test
    void shouldThrowWhenCustomerNotFoundOnDelete() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class,
                () -> customerService.deleteCustomer(1L));

        verify(customerRepository).findById(1L);
        verify(customerRepository, never()).delete(any());
    }
    private Customer generateCustomer() {
        var customer = new Customer(  "Max",
                "Mustermann",
                LocalDate.of(1990, 1, 1),
                CustomerGender.MALE);
        customer.addContact(new CustomerContact(ContactCategory.EMAIL, "max.Mustermann@test.com"));
        return customer;
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

}

