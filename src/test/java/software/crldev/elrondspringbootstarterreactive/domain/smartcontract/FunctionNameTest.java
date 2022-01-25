package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FunctionNameTest {

    @Test
    void fromString() {
        assertThrows(IllegalArgumentException.class, () -> FunctionName.fromString(null));
        assertThrows(IllegalArgumentException.class, () -> FunctionName.fromString(""));

        assertEquals("saveToken", FunctionName.fromString("    saveToken ").getValue());
    }
}