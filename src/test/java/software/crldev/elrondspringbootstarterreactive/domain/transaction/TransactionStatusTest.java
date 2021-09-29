package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import software.crldev.elrondspringbootstarterreactive.domain.transaction.TransactionStatus;
import software.crldev.elrondspringbootstarterreactive.error.exception.InvalidTransactionStatusException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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