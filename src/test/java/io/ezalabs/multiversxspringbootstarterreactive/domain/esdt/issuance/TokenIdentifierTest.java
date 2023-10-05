package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.issuance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;

class TokenIdentifierTest {

  @Test
  void fromString() {
    var t1 = TokenIdentifier.fromString("MoJi-J29900");

    assertEquals("MOJI-j29900", t1.getValue());
    assertEquals(Hex.toHexString("MOJI-j29900".getBytes()), t1.getHex());

    assertThrows(IllegalArgumentException.class, () -> TokenIdentifier.fromString(null));
    assertThrows(IllegalArgumentException.class, () -> TokenIdentifier.fromString(" "));
    assertThrows(IllegalArgumentException.class, () -> TokenIdentifier.fromString("tkn--"));
    assertThrows(IllegalArgumentException.class, () -> TokenIdentifier.fromString("AS-90fa9 "));
  }
}