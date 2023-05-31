package software.crldev.multiversxspringbootstarterreactive.domain.esdt.issuance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenDecimals;
import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

class TokenDecimalsTest {

  @Test
  void fromNumber() {
    var s1 = TokenDecimals.fromNumber(2);

    assertEquals("02", s1.getHex());

    assertEquals(ErrorMessage.TOKEN_DECIMALS.getValue(),
        assertThrows(IllegalArgumentException.class,
            () -> TokenDecimals.fromNumber(-1)).getMessage());
    assertEquals(ErrorMessage.TOKEN_DECIMALS.getValue(),
        assertThrows(IllegalArgumentException.class,
            () -> TokenDecimals.fromNumber(19)).getMessage());
    assertEquals(ErrorMessage.TOKEN_DECIMALS.getValue(),
        assertThrows(IllegalArgumentException.class,
            () -> TokenDecimals.fromNumber(7)).getMessage());
  }

  @Test
  void fromString() {
    var s1 = TokenDecimals.fromString("6");
    assertEquals("06", s1.getHex());

    assertThrows(NumberFormatException.class, () -> TokenDecimals.fromString("2x"));

  }

}