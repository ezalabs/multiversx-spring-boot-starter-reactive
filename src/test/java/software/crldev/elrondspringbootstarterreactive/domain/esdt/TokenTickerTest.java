package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TokenTickerTest {

    @Test
    void fromString() {

        var t1 = TokenTicker.fromString(" UsT ");

        assertEquals("UST", t1.getValue());
        assertEquals("UST", new String(Hex.decode(t1.getHex())));

        assertThrows(IllegalArgumentException.class, () -> TokenTicker.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> TokenTicker.fromString(" "));
        assertThrows(IllegalArgumentException.class, () -> TokenTicker.fromString("tkn--"));
        assertThrows(IllegalArgumentException.class, () -> TokenTicker.fromString("   tk "));
        assertThrows(IllegalArgumentException.class, () -> TokenTicker.fromString("aaaaaaaaaaa"));
    }

}