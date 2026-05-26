package de.meziane.services.customer.api.dto;

import de.meziane.common.model.CustomerGender;

import java.time.LocalDate;
import java.util.List;

public record CustomerResponse(Long id,
                               String givenName,
                               String familyName,
                               LocalDate dob,
                               CustomerGender gender,
                               List<CustomerContactResponse> contacts) {
}
