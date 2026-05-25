package de.meziane.common.model;

import java.util.Objects;
import java.util.regex.Pattern;

public record EmailAddress(String value) {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public EmailAddress {
        Objects.requireNonNull(value, "email must not be null");
        value = value.trim().toLowerCase();

        if (value.isBlank()) {
            throw new IllegalArgumentException("email must not be blank");
        }

        if (!EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("email format is invalid");
        }
    }
}
