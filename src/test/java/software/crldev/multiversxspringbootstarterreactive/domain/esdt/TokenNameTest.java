package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenName;

class TokenNameTest {

  @Test
  void fromString() {

    var t1 = TokenName.fromString(" UsT ");

    assertEquals("UsT", t1.getValue());
    assertEquals("UsT", new String(Hex.decode(t1.getHex())));

    assertThrows(IllegalArgumentException.class, () -> TokenName.fromString(null));
    assertThrows(IllegalArgumentException.class, () -> TokenName.fromString(" "));
    assertThrows(IllegalArgumentException.class, () -> TokenName.fromString("TOKKEN--"));
    assertThrows(IllegalArgumentException.class, () -> TokenName.fromString("   aa "));
    assertThrows(IllegalArgumentException.class, () -> TokenName.fromString("aaaaaaaaaaaaaaaaaaaaa"));
  }
}