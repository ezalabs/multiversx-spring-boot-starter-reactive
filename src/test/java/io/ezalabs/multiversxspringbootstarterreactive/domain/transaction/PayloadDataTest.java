package io.ezalabs.multiversxspringbootstarterreactive.domain.transaction;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;
import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

class PayloadDataTest {

  String dataString = "crldev x multiversX";
  byte[] dataAsBuffer = dataString.getBytes();
  String encoded = Base64.getEncoder().encodeToString(dataAsBuffer);

  @Test
  void fromString() {
    assertArrayEquals(dataAsBuffer, PayloadData.fromString(dataString).getBuffer());
  }

  @Test
  void fromEncoded() {
    var emptyEncoded = Strings.EMPTY;

    assertArrayEquals(dataAsBuffer, PayloadData.fromBase64Encoded(encoded).getBuffer());
    assertTrue(PayloadData.fromBase64Encoded(emptyEncoded).isEmpty());
  }

  @Test
  void length() {
    assertEquals(dataString.length(), PayloadData.fromBase64Encoded(encoded).length());
  }

  @Test
  void encoded() {
    assertEquals(encoded, PayloadData.fromString(dataString).encoded());
  }
}