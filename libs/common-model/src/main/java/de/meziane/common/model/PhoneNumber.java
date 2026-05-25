package de.meziane.common.model;

import java.util.Objects;
import java.util.regex.Pattern;

public record PhoneNumber(String value) {

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^\\+?[0-9()\\-\\s/]{6,30}$");

    public PhoneNumber {
        Objects.requireNonNull(value, "phone number must not be null");
        value = value.trim();

        if (value.isBlank()) {
            throw new IllegalArgumentException("phone number must not be blank");
        }

        if (!PHONE_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("phone number format is invalid");
        }
    }
}
