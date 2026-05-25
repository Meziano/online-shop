package de.meziane.services.customer.persistence.repository;

import de.meziane.services.customer.persistence.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByFamilyNameIgnoreCase(String familyName);
}
