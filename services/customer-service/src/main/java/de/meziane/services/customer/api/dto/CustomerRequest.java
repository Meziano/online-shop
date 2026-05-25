package de.meziane.services.customer.api.dto;

import de.meziane.common.model.ContactCategory;
import de.meziane.common.model.CustomerGender;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record CustomerRequest(@NotBlank
                              @Size(max = 100)
                              String givenName,

                              @NotBlank
                              @Size(max = 100)
                              String familyName,
                              LocalDate dob,
                              CustomerGender gender,
                              @NotEmpty
                              List<CustomerContactRequest> contacts) {

    @AssertTrue(message = "At least one EMAIL contact is required")
    public boolean hasEmailContact() {
        return contacts != null
                && contacts.stream().anyMatch(c -> c.category() == ContactCategory.EMAIL);
    }
}
