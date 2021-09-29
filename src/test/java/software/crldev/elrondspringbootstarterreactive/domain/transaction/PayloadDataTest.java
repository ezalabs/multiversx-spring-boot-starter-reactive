package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class PayloadDataTest {

    String dataString = "crldev x elrond";
    byte[] dataAsBuffer = dataString.getBytes();
    String encoded = Base64.getEncoder().encodeToString(dataAsBuffer);

    @Test
    void fromString() {
        assertArrayEquals(dataAsBuffer, PayloadData.fromString(dataString).getBuffer());
    }

    @Test
    void fromEncoded() {
        var emptyEncoded = Strings.EMPTY;

        assertArrayEquals(dataAsBuffer, PayloadData.fromEncoded(encoded).getBuffer());
        assertTrue(PayloadData.fromEncoded(emptyEncoded).isEmpty());
    }

    @Test
    void length() {
        assertEquals(dataString.length(), PayloadData.fromEncoded(encoded).length());
    }

    @Test
    void encoded() {
        assertEquals(encoded, PayloadData.fromString(dataString).encoded());
    }
}