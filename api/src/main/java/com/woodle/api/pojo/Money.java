package com.woodle.api.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * 用于表示记账金额, 标准6位小数
 * User: wuqingchao
 * Time: 14-5-9 上午12:06
 */
public final class Money implements Serializable, Comparable<Money>{

    private final long serialVersionUID = 1L;

    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_EVEN;

    private static final MathContext DEF_MC = MathContext.DECIMAL64;

    private static final int PARSE_SCALE = 6;

    private final BigDecimal amount;

    private Money(BigDecimal amount) {
        this.amount = amount;
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money of(double amount) {
        return of(BigDecimal.valueOf(amount));
    }

    public static Money ofMajor(long amountMajor) {
        return of(BigDecimal.valueOf(amountMajor));
    }

    public static Money parseStoreValue(long amount) {
        return of(BigDecimal.valueOf(amount, PARSE_SCALE));
    }

    /**
     * sum
     * @param monies   money need to be sum
     * @return  sum of all monies
     */
    public static Money total(Money... monies) {
        if (monies.length == 0) {
            throw new IllegalArgumentException("Money array must not be empty");
        }
        Money total = monies[0];
        for (int i = 1; i < monies.length; i++) {
            total = total.plus(monies[i]);
        }
        return total;
    }

    private Money with(BigDecimal newAmount) {
        if (newAmount.equals(amount)) {
            return this;
        }
        return new Money(newAmount);
    }

    /**
     * Checks if the amount is zero.
     *
     * @return true if the amount is zero
     */
    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * Checks if the amount is greater than zero.
     *
     * @return true if the amount is greater than zero
     */
    public boolean isPositive() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Checks if the amount is zero or greater.
     *
     * @return true if the amount is zero or greater
     */
    public boolean isPositiveOrZero() {
        return amount.compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * Checks if the amount is less than zero.
     *
     * @return true if the amount is less than zero
     */
    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * check if the amount is less or equal than zero
     *
     * @return true if the amount is zero or less
     */
    public boolean isNegativeOrZero() {
        return amount.compareTo(BigDecimal.ZERO) <= 0;
    }


    public Money plus(Money... toAdds) {
        BigDecimal total = amount;
        for (Money money : toAdds) {
            total = total.add(money.amount,DEF_MC);
        }
        return with(total);
    }

    public Money plus(BigDecimal amountToAdd) {
        if (amountToAdd.compareTo(BigDecimal.ZERO) == 0) {
            return this;
        }
        BigDecimal newAmount = amount.add(amountToAdd,DEF_MC);
        return with(newAmount);
    }

    public Money plus(double amountToAdd) {
        if (amountToAdd == 0) {
            return this;
        }
        BigDecimal newAmount = amount.add(BigDecimal.valueOf(amountToAdd),DEF_MC);
        return with( newAmount);
    }

    public Money minus(Money ... accountMoneys){
        BigDecimal total = amount;
        for (Money money : accountMoneys) {
            total = total.subtract(money.amount,DEF_MC);
        }
        return with(total);
    }

    public Money minus(Money moneyToSubtract) {
        return minus(moneyToSubtract.amount);
    }

    public Money minus(BigDecimal amountToSubtract) {
        if (amountToSubtract.compareTo(BigDecimal.ZERO) == 0) {
            return this;
        }
        BigDecimal newAmount = amount.subtract(amountToSubtract,DEF_MC);
        return with(newAmount);
    }

    public Money minus(double amountToSubtract) {
        if (amountToSubtract == 0) {
            return this;
        }
        BigDecimal newAmount = amount.subtract(BigDecimal.valueOf(amountToSubtract),DEF_MC);
        return with(newAmount);
    }

    public Money minusMajor(long amountToSubtract) {
        if (amountToSubtract == 0) {
            return this;
        }
        BigDecimal newAmount = amount.subtract(BigDecimal.valueOf(amountToSubtract),DEF_MC);
        return with(newAmount);
    }

    public Money minusMinor(long amountToSubtract, int scale) {
        if (amountToSubtract == 0) {
            return this;
        }
        BigDecimal newAmount = amount.subtract(BigDecimal.valueOf(amountToSubtract,scale),DEF_MC);
        return with(newAmount);
    }

    public Money multiply(BigDecimal valueToMultiplyBy) {
        if (valueToMultiplyBy.compareTo(BigDecimal.ONE) == 0) {
            return this;
        }
        BigDecimal newAmount = amount.multiply(valueToMultiplyBy,DEF_MC);
        return with(newAmount);
    }

    public Money multiply(double valueToMultiplyBy) {
        if (valueToMultiplyBy == 1) {
            return this;
        }
        BigDecimal newAmount = amount.multiply(BigDecimal.valueOf(valueToMultiplyBy),DEF_MC);
        return with(newAmount);
    }

    public Money multiply(long valueToMultiplyBy) {
        if (valueToMultiplyBy == 1) {
            return this;
        }
        BigDecimal newAmount = amount.multiply(BigDecimal.valueOf(valueToMultiplyBy),DEF_MC);
        return with(newAmount);
    }


    public Money divide(BigDecimal  value) {
        if (value.compareTo(BigDecimal.ONE) == 0) {
            return this;
        }
        BigDecimal newAmount = amount.divide(value, DEF_MC);
        return with(newAmount);
    }

    public Money divide(double value) {
        if (value == 1) {
            return this;
        }
        BigDecimal newAmount = amount.divide(BigDecimal.valueOf(value), DEF_MC);
        return with(newAmount);
    }


    public Money divide(long value) {
        if (value == 1) {
            return this;
        }
        BigDecimal newAmount = amount.divide(BigDecimal.valueOf(value), DEF_MC);

        return with(newAmount);
    }

    public Money negate() {
        if (isZero()) {
            return this;
        }
        return with(amount.negate());
    }
    public Money abs() {
        return (isNegative() ? negate() : this);
    }


    public boolean isGreaterThan(Money other) {
        return compareTo(other) > 0;
    }

    public boolean isLessThan(Money other) {
        return compareTo(other) < 0;
    }


    @Override
    public int compareTo(Money o) {
        return amount.compareTo(o.amount);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Money) {
            Money otherMoney = (Money) other;
            return amount.equals(otherMoney.amount);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 7 * amount.hashCode();
    }

    public long toStoreValue() {
        return amount.movePointRight(PARSE_SCALE).setScale(0, DEFAULT_ROUNDING).longValue();
    }

    public BigDecimal toStoreDecimal() {
        return amount.setScale(PARSE_SCALE, DEFAULT_ROUNDING);
    }

    public String toString() {
        return amount.toString();
    }

    public String toMoneyString() {
        return amount.setScale(2, DEFAULT_ROUNDING).toString();
    }

}
