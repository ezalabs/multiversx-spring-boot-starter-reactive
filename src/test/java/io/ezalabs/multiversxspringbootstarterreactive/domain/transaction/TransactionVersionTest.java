package io.ezalabs.multiversxspringbootstarterreactive.domain.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.TransactionConstants;
import io.ezalabs.multiversxspringbootstarterreactive.error.exception.InvalidTransactionVersionException;
import org.junit.jupiter.api.Test;

class TransactionVersionTest {

  @Test
  void create() {
    assertEquals(2, TransactionVersion.fromInteger(2).getValue());
    assertThrows(InvalidTransactionVersionException.class, () -> TransactionVersion.fromInteger(-1));
  }

  @Test
  void withDefaultVersion() {
    assertEquals(TransactionConstants.TRANSACTION_VERSION_DEFAULT, TransactionVersion.withDefaultVersion().getValue());
  }

  @Test
  void withTxHashSignVersion() {
    assertEquals(TransactionConstants.TRANSACTION_VERSION_TX_HASH_SIGN, TransactionVersion.withTransactionHashSignVersion().getValue());
  }
}