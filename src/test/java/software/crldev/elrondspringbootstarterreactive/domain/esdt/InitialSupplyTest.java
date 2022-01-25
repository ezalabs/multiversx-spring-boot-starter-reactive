package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InitialSupplyTest {

    @Test
    void fromNumber() {
        var s1 = InitialSupply.fromNumber(BigInteger.TEN, 2);

        assertEquals(BigInteger.TEN, s1.getSupply());
        assertEquals(2, s1.getDecimals());
        assertEquals("10.00", s1.getValue());

        assertEquals("10", new String(Hex.decode(s1.getSupplyHex())));
        assertEquals("2", new String(Hex.decode(s1.getDecimalsHex())));

        assertEquals(ErrorMessage.INITIAL_SUPPLY.getValue(),
                assertThrows(IllegalArgumentException.class,
                        () -> InitialSupply.fromNumber(BigInteger.valueOf(349L), 2)).getMessage());
        assertEquals(ErrorMessage.TOKEN_DECIMALS.getValue(),
                assertThrows(IllegalArgumentException.class,
                        () -> InitialSupply.fromNumber(BigInteger.TEN, -1)).getMessage());
        assertEquals(ErrorMessage.TOKEN_DECIMALS.getValue(),
                assertThrows(IllegalArgumentException.class,
                        () -> InitialSupply.fromNumber(BigInteger.TEN, 19)).getMessage());
        assertEquals(ErrorMessage.TOKEN_DECIMALS.getValue(),
                assertThrows(IllegalArgumentException.class,
                        () -> InitialSupply.fromNumber(BigInteger.TEN, 7)).getMessage());
    }

    @Test
    void fromString() {
        var s1 = InitialSupply.fromString("10 ", " 2");

        assertEquals(BigInteger.TEN, s1.getSupply());
        assertEquals(2, s1.getDecimals());

        assertThrows(NumberFormatException.class, () -> InitialSupply.fromString("120ks", "2"));
        assertThrows(NumberFormatException.class, () -> InitialSupply.fromString("10", "2x"));

    }
}