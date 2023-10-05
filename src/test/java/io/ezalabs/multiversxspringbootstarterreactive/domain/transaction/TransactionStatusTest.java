package io.ezalabs.multiversxspringbootstarterreactive.domain.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.ezalabs.multiversxspringbootstarterreactive.error.exception.InvalidTransactionStatusException;
import org.junit.jupiter.api.Test;

class TransactionStatusTest {

  @Test
  void create() {
    assertEquals(TransactionStatus.FAIL, TransactionStatus.fromString("FaiL"));
    assertEquals(TransactionStatus.SUCCESS, TransactionStatus.fromString("suCCESS"));
    assertEquals(TransactionStatus.INVALID, TransactionStatus.fromString("INVAlid"));
    assertEquals(TransactionStatus.PENDING, TransactionStatus.fromString("pending"));
  }

  @Test
  void error() {
    assertThrows(InvalidTransactionStatusException.class, () -> TransactionStatus.fromString("unrecognized"));
  }
}