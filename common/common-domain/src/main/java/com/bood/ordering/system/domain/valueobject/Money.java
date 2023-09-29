package com.bood.ordering.system.domain.valueobject;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
    public static final Money ZERO = new Money(BigDecimal.ZERO);
    private final BigDecimal amount;

    public Money(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isGreaterThanZero() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }
    public boolean isGreaterThen(Money other) {
        return amount != null && amount.compareTo(other.amount) > 0;
    }
    public Money add(Money other) {
        return new Money(setScale(amount.add(other.amount)));
    }
    public Money subtract(Money other) {
        return new Money(setScale(amount.subtract(other.amount)));
    }
    public Money multiply(int quantity) {
        return new Money(setScale(amount.multiply(new BigDecimal(quantity))));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
    private BigDecimal setScale(BigDecimal amount) {
        return amount.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
