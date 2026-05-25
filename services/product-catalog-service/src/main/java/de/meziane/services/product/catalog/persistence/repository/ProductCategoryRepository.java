package de.meziane.services.product.catalog.persistence.repository;

import de.meziane.services.product.catalog.persistence.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
}
