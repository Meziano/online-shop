package de.meziane.common.model;

import java.time.Instant;
import java.util.Objects;

public record ErrorResponse(Instant timestamp,
                            int status,
                            String error,
                            String message,
                            String path) {
    public ErrorResponse {
        Objects.requireNonNull(timestamp, "timestamp must not be null");
        error = normalize(error, "error");
        message = normalize(message, "message");
        path = normalize(path, "path");

        if (status < 100 || status > 599) {
            throw new IllegalArgumentException("status must be a valid HTTP status code");
        }
    }

    public static ErrorResponse of(int status, String error, String message, String path) {
        return new ErrorResponse(Instant.now(), status, error, message, path);
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
