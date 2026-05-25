package de.meziane.common.model;

import java.util.Objects;

public record AddressValue(String street,
                           String houseNumber,
                           String postalCode,
                           String city,
                           String countryCode) {

    public AddressValue {
        street = normalize(street, "street");
        houseNumber = normalize(houseNumber, "houseNumber");
        postalCode = normalize(postalCode, "postalCode");
        city = normalize(city, "city");
        countryCode = normalize(countryCode, "countryCode").toUpperCase();

        if (countryCode.length() != 2) {
            throw new IllegalArgumentException("countryCode must be an ISO-3166 alpha-2 code");
        }
    }

    private static String normalize(String value, String fieldName) {
        Objects.requireNonNull(value, fieldName + " must not be null");
        String normalized = value.trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank");
        }
        return normalized;
    }
}
