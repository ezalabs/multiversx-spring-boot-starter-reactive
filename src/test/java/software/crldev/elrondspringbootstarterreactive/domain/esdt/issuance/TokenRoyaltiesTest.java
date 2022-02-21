package software.crldev.elrondspringbootstarterreactive.domain.esdt.issuance;

import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenRoyalties;

import static org.junit.jupiter.api.Assertions.*;

class TokenRoyaltiesTest {

    @Test
    void create() {
        var roy = TokenRoyalties.fromNumber(5);

        assertEquals("05", roy.getHex());

        assertThrows(IllegalArgumentException.class, () -> TokenRoyalties.fromNumber(null));
        assertThrows(IllegalArgumentException.class, () -> TokenRoyalties.fromNumber(-1));
        assertThrows(IllegalArgumentException.class, () -> TokenRoyalties.fromNumber(10001));
    }

}