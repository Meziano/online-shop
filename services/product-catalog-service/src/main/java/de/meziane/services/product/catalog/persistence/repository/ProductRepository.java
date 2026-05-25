package de.meziane.services.product.catalog.persistence.repository;

import de.meziane.services.product.catalog.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
