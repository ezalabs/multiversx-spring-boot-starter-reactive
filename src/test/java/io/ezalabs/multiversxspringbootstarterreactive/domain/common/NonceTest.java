package io.ezalabs.multiversxspringbootstarterreactive.domain.common;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.ezalabs.multiversxspringbootstarterreactive.error.exception.InvalidNonceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NonceTest {

  @Test
  void fromLong() {
    Assertions.assertDoesNotThrow(() -> Nonce.fromLong(1L));
    assertThrows(InvalidNonceException.class, () -> Nonce.fromLong(-1L));
  }

  @Test
  void zero() {
    assertEquals(0, Nonce.zero().getValue());
  }

}