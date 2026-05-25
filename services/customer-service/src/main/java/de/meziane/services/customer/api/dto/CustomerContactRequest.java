package de.meziane.services.customer.api.dto;

import de.meziane.common.model.ContactCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerContactRequest(@NotNull
                                     ContactCategory category,
                                     @NotBlank
                                     @Size(max = 500)
                                     String value) {
}
