package de.meziane.services.customer.api.dto;

import de.meziane.common.model.CustomerGender;
import de.meziane.services.customer.persistence.entity.CustomerContact;

import java.time.LocalDate;
import java.util.List;

public record CustomerResponse(Long id,
                               String firstName,
                               String lastName,
                               LocalDate dob,
                               CustomerGender gender,
                               List<CustomerContactResponse> contacts) {
}
