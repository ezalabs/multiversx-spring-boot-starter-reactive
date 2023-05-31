package software.crldev.multiversxspringbootstarterreactive.domain.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.TransactionConstants.TRANSACTION_VERSION_DEFAULT;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.TransactionConstants.TRANSACTION_VERSION_TX_HASH_SIGN;

import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.error.exception.InvalidTransactionVersionException;

class TransactionVersionTest {

  @Test
  void create() {
    assertEquals(2, TransactionVersion.fromInteger(2).getValue());
    assertThrows(InvalidTransactionVersionException.class, () -> TransactionVersion.fromInteger(-1));
  }

  @Test
  void withDefaultVersion() {
    assertEquals(TRANSACTION_VERSION_DEFAULT, TransactionVersion.withDefaultVersion().getValue());
  }

  @Test
  void withTxHashSignVersion() {
    assertEquals(TRANSACTION_VERSION_TX_HASH_SIGN, TransactionVersion.withTransactionHashSignVersion().getValue());
  }
}