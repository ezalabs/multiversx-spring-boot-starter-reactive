package software.crldev.multiversxspringbootstarterreactive.domain.smartcontract;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;

class FunctionArgTest {

  @Test
  void fromString() {
    assertEquals("argument list", FunctionArg.fromString(" argument list   ").toString());
    assertEquals("617267756d656e74206c697374", FunctionArg.fromString("argument list ").getHex());

    assertThrows(IllegalArgumentException.class, () -> FunctionArg.fromString(""));
    assertThrows(IllegalArgumentException.class, () -> FunctionArg.fromString(null));
  }

  @Test
  void fromNumber() {
    assertEquals("2", FunctionArg.fromNumber(BigInteger.TWO).toString());
    assertEquals("02", FunctionArg.fromNumber(BigInteger.TWO).getHex());

    assertThrows(IllegalArgumentException.class, () -> FunctionArg.fromNumber(null));
    assertThrows(IllegalArgumentException.class, () -> FunctionArg.fromNumber(BigInteger.valueOf(-1L)));
  }

}