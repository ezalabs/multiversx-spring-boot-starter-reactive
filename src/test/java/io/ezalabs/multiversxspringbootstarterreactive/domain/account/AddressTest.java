package io.ezalabs.multiversxspringbootstarterreactive.domain.account;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.AddressConstants;
import io.ezalabs.multiversxspringbootstarterreactive.error.exception.CannotDecodeBech32AddressException;
import io.ezalabs.multiversxspringbootstarterreactive.error.exception.InvalidHexValueException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddressTest {

  String hexPublicKey = "bf86ad970aa3c93c2382889c0a4fba4728b5b851634d57ccd65dcddeda8c8bb0";
  String bech32 = "erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj";

  @Test
  void fromHex() {
    Assertions.assertDoesNotThrow(() -> Address.fromHex(hexPublicKey));
    assertThrows(InvalidHexValueException.class, () -> Address.fromHex(hexPublicKey.concat("1234")));
  }

  @Test
  void fromBech32() {
    Assertions.assertDoesNotThrow(() -> Address.fromBech32(bech32));
    assertThrows(CannotDecodeBech32AddressException.class, () -> Address.fromBech32(bech32.concat("123")));
  }

  @Test
  void hex() {
    var address = Address.fromBech32(bech32);
    assertEquals(hexPublicKey, address.getHex());
  }

  @Test
  void publicKey() {
    var address = Address.fromBech32(bech32);
    assertArrayEquals(Hex.decode(hexPublicKey), address.getPublicKey());
  }

  @Test
  void bech32() {
    var address = Address.fromHex(hexPublicKey);
    assertEquals(bech32, address.getBech32());
  }

  @Test
  void zero() {
    assertEquals(AddressConstants.ZERO_PUBKEY_STRING, Address.zero().getHex());
  }
}