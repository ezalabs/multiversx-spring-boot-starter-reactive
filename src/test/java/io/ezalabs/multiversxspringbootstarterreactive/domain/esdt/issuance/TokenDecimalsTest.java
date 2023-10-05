package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.issuance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenDecimals;

class TokenDecimalsTest {

  @Test
  void fromNumber() {
    var s1 = TokenDecimals.fromNumber(2);

    assertEquals("02", s1.getHex());

    Assertions.assertEquals(ErrorMessage.TOKEN_DECIMALS.getValue(),
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