package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenInitialSupply;
import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

class TokenInitialSupplyTest {

  @Test
  void fromNumber() {
    var s1 = TokenInitialSupply
        .fromNumber(BigInteger.valueOf(31_000_000L));

    assertEquals("01d905c0", s1.getHex());

    assertEquals(ErrorMessage.INITIAL_SUPPLY.getValue(),
        assertThrows(IllegalArgumentException.class,
            () -> TokenInitialSupply.fromNumber(BigInteger.valueOf(-500L))).getMessage());
  }

  @Test
  void fromString() {
    var s1 = TokenInitialSupply.fromString("32000000");

    assertEquals(BigInteger.valueOf(32_000_000L), s1.getSupply());
    assertEquals("01e84800", s1.getHex());

    assertThrows(NumberFormatException.class, () -> TokenInitialSupply.fromString("120ks"));
  }
}