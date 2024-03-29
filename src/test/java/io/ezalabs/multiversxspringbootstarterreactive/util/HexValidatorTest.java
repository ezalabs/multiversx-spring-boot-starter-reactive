package io.ezalabs.multiversxspringbootstarterreactive.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.AddressConstants;
import org.junit.jupiter.api.Test;

class HexValidatorTest {

  @Test
  void hexValid() {
    assertTrue(
        HexValidator.isHexValid("bf86ad970aa3c93c2382889c0a4fba4728b5b851634d57ccd65dcddeda8c8bb0", AddressConstants.PUBKEY_HEX_LENGTH));
  }

  @Test
  void hexInvalid() {
    assertFalse(HexValidator.isHexValid("bf86ad970aa3c93c2382889c0a4fba4728b5b851634d57ccd65dcddeda8b0", AddressConstants.PUBKEY_HEX_LENGTH));
  }

  @Test
  void processNumberHexARg() {
    assertEquals("034851", HexValidator.processNumberHexArgument("34851"));
    assertEquals("5408", HexValidator.processNumberHexArgument("5408"));
  }
}
