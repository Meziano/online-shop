package de.meziane.services.product.catalog.api.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(@NotBlank
                             @Size(max = 250)
                             String title,

                             boolean active,

                             @NotBlank
                             @Size(max = 64)
                             String sku,

                             @NotNull
                             @DecimalMin(value = "0.0", inclusive = true)
                             BigDecimal price,

                             @NotBlank
                             @Pattern(regexp = "^[A-Z]{3}$")
                             String currency,

                             @NotNull
                             Long categoryId) {
}
