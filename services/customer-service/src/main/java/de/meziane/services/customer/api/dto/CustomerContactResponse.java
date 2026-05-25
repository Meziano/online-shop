package de.meziane.services.customer.api.dto;


import de.meziane.common.model.ContactCategory;

public record CustomerContactResponse(ContactCategory category,
                                      String value) {
}
