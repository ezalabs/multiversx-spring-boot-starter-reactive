package software.crldev.elrondspringbootstarterreactive.domain.common;

import software.crldev.elrondspringbootstarterreactive.config.CurrencyConstants;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.error.exception.InvalidBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

class BalanceTest {

    String oneEgldString = CurrencyConstants.ONE_EGLD_STRING;

    @Test
    void fromEgld() {
        var expected = oneEgldString;
        var balance = Balance.fromEgld(1.00);

        assertEquals(new BigInteger(expected), balance.getValue());
        assertEquals(expected, balance.toString());

        var expectedWithDecimal = "1120500000000000000";
        var balanceWithDecimal = Balance.fromEgld(1.1205);

        assertEquals(new BigInteger(expectedWithDecimal), balanceWithDecimal.getValue());
        assertEquals(expectedWithDecimal, balanceWithDecimal.toString());
    }

    @Test
    void fromString_positive() {
        assertDoesNotThrow(() -> Balance.fromString((oneEgldString)));
    }

    @Test
    void fromString_negative() {
        assertThrows(InvalidBalanceException.class, () -> Balance.fromString(("-".concat(oneEgldString))));
    }

    @Test
    void fromNumber() {
        assertDoesNotThrow(() -> Balance.fromNumber(new BigInteger(oneEgldString)));
    }

    @Test
    void zero() {
        assertEquals(BigInteger.ZERO, Balance.zero().getValue());
    }

    @Test
    void isZero() {
        assertTrue(Balance.zero().isZero());
        assertFalse(Balance.fromString(oneEgldString).isZero());
    }

    @Test
    void isSet() {
        assertFalse(Balance.zero().isSet());
        assertTrue(Balance.fromString(oneEgldString).isSet());
    }

    @Test
    void toCurrencyString() {
        var balance1 = Balance.fromString("5100000000000000000");
        assertEquals("5.10EGLD", balance1.toCurrencyString());

        var balance2 = Balance.fromEgld(51.7689);
        assertEquals("51.76EGLD", balance2.toCurrencyString());
    }

}