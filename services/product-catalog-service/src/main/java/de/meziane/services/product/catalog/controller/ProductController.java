package de.meziane.services.product.catalog.controller;

import de.meziane.services.product.catalog.api.dto.ProductRequest;
import de.meziane.services.product.catalog.api.dto.ProductResponse;
import de.meziane.services.product.catalog.mapper.ProductMapper;
import de.meziane.services.product.catalog.persistence.entity.Product;
import de.meziane.services.product.catalog.persistence.entity.ProductCategory;
import de.meziane.services.product.catalog.persistence.repository.ProductCategoryRepository;
import de.meziane.services.product.catalog.persistence.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductCategoryRepository productCategoryRepository;

    @GetMapping("/{id}")
    public ProductResponse getProduct(@PathVariable long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        return ProductMapper.toResponse(product);
    }

    @PostMapping
    public ProductResponse createProduct(@Valid @RequestBody ProductRequest prodReq) {
        ProductCategory category = productCategoryRepository.getReferenceById(prodReq.categoryId());
        Product product = ProductMapper.toProduct(prodReq, category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.toResponse(savedProduct);
    }




}
