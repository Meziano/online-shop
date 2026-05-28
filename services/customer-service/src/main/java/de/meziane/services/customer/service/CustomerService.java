package de.meziane.services.customer.service;

import de.meziane.services.customer.api.dto.CustomerRequest;
import de.meziane.services.customer.api.dto.CustomerResponse;
import de.meziane.services.customer.mapper.CustomerMapper;
import de.meziane.services.customer.persistence.entity.Customer;
import de.meziane.services.customer.persistence.entity.CustomerContact;
import de.meziane.services.customer.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest custReq) {
        Customer customer = CustomerMapper.toCustomer(custReq);
        Customer saved = customerRepository.save(customer);
        return CustomerMapper.toResponse(saved);
    }

    public CustomerResponse getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return CustomerMapper.toResponse(customer);
    }

    public CustomerResponse updateCustomer(Long id, CustomerRequest customerRequest) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        existingCustomer.changeGivenName(customerRequest.givenName());
        existingCustomer.changeFamilyName(customerRequest.familyName());
        existingCustomer.changeDob(customerRequest.dob());
        existingCustomer.changeGender(customerRequest.gender());        ;
        List<CustomerContact> oldContacts = new ArrayList<>(existingCustomer.getContacts());
        oldContacts.forEach(existingCustomer::removeContact);
        customerRequest.contacts().stream()
                .map(CustomerMapper::toContactEntity)
                .forEach(existingCustomer::addContact);
        Customer saved = customerRepository.save(existingCustomer);
        return CustomerMapper.toResponse(saved);
    }

    public void deleteCustomer(Long id) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        customerRepository.delete(existingCustomer);
    }
}
