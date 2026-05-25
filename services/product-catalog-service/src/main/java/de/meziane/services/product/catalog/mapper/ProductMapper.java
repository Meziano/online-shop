package de.meziane.services.product.catalog.mapper;

import de.meziane.services.product.catalog.api.dto.ProductRequest;
import de.meziane.services.product.catalog.api.dto.ProductResponse;
import de.meziane.services.product.catalog.persistence.entity.Product;
import de.meziane.services.product.catalog.persistence.entity.ProductCategory;

public final class ProductMapper {

    public static ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getProductType(),
                product.isActive(),
                product.getSku(),
                product.getTitle(),
                product.getPrice(),
                product.getCurrency(),
                product.getCategory().getId(),
                product.getCategory().getName()
        );
    }

    public static Product toProduct(ProductRequest productRequest, ProductCategory category) {

        Product product = new Product(
                productRequest.title(),
                productRequest.price(),
                productRequest.sku(),
                productRequest.currency(),
                category
        );

        return product;
    }
}
