package de.meziane.services.customer.controller;


import de.meziane.services.customer.api.dto.CustomerRequest;
import de.meziane.services.customer.api.dto.CustomerResponse;
import de.meziane.services.customer.mapper.CustomerMapper;
import de.meziane.services.customer.persistence.entity.Customer;
import de.meziane.services.customer.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/customers")
@RestController
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest custReq) {
        CustomerResponse customerResponse = customerService.createCustomer(custReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequest request) {
        CustomerResponse customerResponse = customerService.updateCustomer(id, request);
        return ResponseEntity.ok(customerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}