package software.crldev.multiversxspringbootstarterreactive.domain.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.error.exception.NegativeGasException;

class GasTest {

  @Test
  void gasLimitAndPrice_fromNumber() {
    var expected = BigInteger.TEN;

    assertEquals(expected, GasLimit.fromNumber(BigInteger.TEN).getValue());
    assertEquals(expected, GasPrice.fromNumber(BigInteger.TEN).getValue());

    assertThrows(NegativeGasException.class, () -> GasLimit.fromNumber(new BigInteger("-5000")));
    assertThrows(NegativeGasException.class, () -> GasPrice.fromNumber(new BigInteger("-5000")));
  }

  @Test
  void gasLimitAndPrice_fromString() {
    var expected = "10000";

    assertEquals(expected, GasLimit.fromString(expected).toString());
    assertEquals(expected, GasPrice.fromString(expected).toString());
  }
}