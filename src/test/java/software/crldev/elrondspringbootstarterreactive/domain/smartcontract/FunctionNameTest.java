package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FunctionNameTest {

    @Test
    void fromString() {
        assertThrows(IllegalArgumentException.class, () -> FunctionName.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> FunctionName.fromString(""));

        assertEquals("saveToken", FunctionName.fromString("    saveToken ").getValue());
        assertEquals("73617665546f6b656e", FunctionName.fromString("    saveToken ").getHex());
        assertEquals(Strings.EMPTY, FunctionName.empty().getValue());
    }
}