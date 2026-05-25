package de.meziane.services.product.catalog.api.dto;

import de.meziane.common.model.ProductType;

import java.math.BigDecimal;

public record ProductResponse(Long id,
                              ProductType productType,
                              boolean active,
                              String sku,
                              String title,
                              BigDecimal price,
                              String currency,
                              Long categoryId,
                              String categoryName) {
}
