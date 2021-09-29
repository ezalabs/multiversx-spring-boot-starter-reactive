package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import software.crldev.elrondspringbootstarterreactive.domain.transaction.TransactionVersion;
import software.crldev.elrondspringbootstarterreactive.error.exception.InvalidTransactionVersionException;
import org.junit.jupiter.api.Test;

import static software.crldev.elrondspringbootstarterreactive.config.TransactionConstants.TRANSACTION_VERSION_DEFAULT;
import static software.crldev.elrondspringbootstarterreactive.config.TransactionConstants.TRANSACTION_VERSION_TX_HASH_SIGN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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