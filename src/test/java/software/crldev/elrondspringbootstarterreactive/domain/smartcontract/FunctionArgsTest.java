package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.error.exception.FunctionArgsException;

import static org.junit.jupiter.api.Assertions.*;

class FunctionArgsTest {

    @Test
    void fromString() {
        var expected = "@6f6e65@74776f@7468726565";

        assertThrows(FunctionArgsException.class, () -> FunctionArgs.fromString("one", null, "three"));
        assertThrows(FunctionArgsException.class, () -> FunctionArgs.fromString("one", null, "three"));
        assertThrows(FunctionArgsException.class, () -> FunctionArgs.fromString("one", "", "three"));

        assertEquals(expected, FunctionArgs.fromString("one", "two", "three").toString());
    }

    @Test
    void fromString_exception() {
        assertThrows(FunctionArgsException.class, () -> FunctionArgs.fromString(new String[]{}));
        assertThrows(FunctionArgsException.class, () -> FunctionArgs.fromString("one", null, "three"));
        assertThrows(FunctionArgsException.class, () -> FunctionArgs.fromString("one", "", "three"));
    }

    @Test
    void empty() {
        assertTrue(FunctionArgs.empty().getValue().isEmpty());
    }
}