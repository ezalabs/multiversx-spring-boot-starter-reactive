package software.crldev.elrondspringbootstarterreactive.domain.common;

import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.error.exception.InvalidNonceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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