package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.issuance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenRoyalties;

class TokenRoyaltiesTest {

  @Test
  void create() {
    var roy = TokenRoyalties.fromNumber(5);

    assertEquals("05", roy.getHex());

    assertThrows(IllegalArgumentException.class, () -> TokenRoyalties.fromNumber(null));
    assertThrows(IllegalArgumentException.class, () -> TokenRoyalties.fromNumber(-1));
    assertThrows(IllegalArgumentException.class, () -> TokenRoyalties.fromNumber(10001));
  }

}