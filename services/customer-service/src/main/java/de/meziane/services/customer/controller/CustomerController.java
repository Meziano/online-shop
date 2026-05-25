package de.meziane.services.customer.controller;


import de.meziane.services.customer.api.dto.CustomerRequest;
import de.meziane.services.customer.api.dto.CustomerResponse;
import de.meziane.services.customer.mapper.CustomerMapper;
import de.meziane.services.customer.persistence.entity.Customer;
import de.meziane.services.customer.persistence.repository.CustomerRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    @Autowired
    private final CustomerRepository customerRepository;

    @GetMapping("/{id}")
    public CustomerResponse getCustomer(@PathVariable long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return CustomerMapper.toResponse(customer);
    }

    @PostMapping
    public CustomerResponse createCustomer(@Valid @RequestBody CustomerRequest custReq) {
        Customer customer = CustomerMapper.toCustomer(custReq);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.toResponse(savedCustomer);
    }
}