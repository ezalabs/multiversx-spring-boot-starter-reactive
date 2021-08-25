package com.crldev.elrondspringbootstarterreactive.domain.transaction;

import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasPrice;
import software.crldev.elrondspringbootstarterreactive.error.exception.NegativeGasException;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GasTest {

    @Test
    void gasLimitAndPrice_fromNumber() {
        var expected = BigInteger.TEN;

        assertEquals(expected, GasLimit.fromNumber(BigInteger.TEN).getValue());
        assertEquals(expected, GasPrice.fromNumber(BigInteger.TEN).getValue());

        assertThrows(NegativeGasException.class, () -> GasLimit.fromNumber(new BigInteger("-5000")));
        assertThrows(NegativeGasException.class, () -> GasPrice.fromNumber(new BigInteger("-5000")));
    }

    @Test
    void gasLimitAndPrice_fromString() {
        var expected = "10000";

        assertEquals(expected, GasLimit.fromString(expected).toString());
        assertEquals(expected, GasPrice.fromString(expected).toString());
    }
}