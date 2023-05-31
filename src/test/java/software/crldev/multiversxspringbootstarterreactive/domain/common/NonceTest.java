package software.crldev.multiversxspringbootstarterreactive.domain.common;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.error.exception.InvalidNonceException;

class NonceTest {

  @Test
  void fromLong() {
    assertDoesNotThrow(() -> Nonce.fromLong(1L));
    assertThrows(InvalidNonceException.class, () -> Nonce.fromLong(-1L));
  }

  @Test
  void zero() {
    assertEquals(0, Nonce.zero().getValue());
  }

}