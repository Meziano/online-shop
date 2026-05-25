package de.meziane.common.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public record Money(BigDecimal amount, Currency currency) {

    public Money {
        Objects.requireNonNull(amount, "amount must not be null");
        Objects.requireNonNull(currency, "currency must not be null");

        amount = amount.stripTrailingZeros();

        if (amount.scale() > 2) {
            throw new IllegalArgumentException("amount must not have more than 2 fraction digits");
        }
    }

    public static Money of(BigDecimal amount, String currencyCode) {
        return new Money(amount, Currency.getInstance(currencyCode));
    }

    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    public Money subtract(Money other) {
        requireSameCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other, "other money must not be null");
        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException("currencies must match");
        }
    }
}
