package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FunctionArgTest {

    @Test
    void fromString() {
        assertEquals("argument", FunctionArg.fromString(" argument ").toString());
        assertEquals("617267756d656e74", FunctionArg.fromString("argument").getHex());

        assertThrows(IllegalArgumentException.class, () -> FunctionArg.fromString(""));
        assertThrows(IllegalArgumentException.class, () -> FunctionArg.fromString(null));
    }

    @Test
    void fromNumber() {
        assertEquals("2", FunctionArg.fromNumber(BigInteger.TWO).toString());
        assertEquals("02", FunctionArg.fromNumber(BigInteger.TWO).getHex());

        assertThrows(IllegalArgumentException.class, () -> FunctionArg.fromNumber(null));
        assertThrows(IllegalArgumentException.class, () -> FunctionArg.fromNumber(BigInteger.valueOf(-1L)));
    }

}