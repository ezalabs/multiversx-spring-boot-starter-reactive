package software.crldev.elrondspringbootstarterreactive.util;

import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.util.HexValidator;

import static software.crldev.elrondspringbootstarterreactive.config.AddressConstants.PUBKEY_HEX_LENGTH;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HexValidatorTest {

    @Test
    void hexValid() {
        assertTrue(HexValidator.isHexValid("bf86ad970aa3c93c2382889c0a4fba4728b5b851634d57ccd65dcddeda8c8bb0", PUBKEY_HEX_LENGTH));
    }

    @Test
    void hexInvalid() {
        assertFalse(HexValidator.isHexValid("bf86ad970aa3c93c2382889c0a4fba4728b5b851634d57ccd65dcddeda8b0", PUBKEY_HEX_LENGTH));
    }
}