package software.crldev.multiversxspringbootstarterreactive.error.exception;

import java.math.BigInteger;
import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidBalanceException extends RuntimeException {

  public InvalidBalanceException(BigInteger balance) {
    super(String.format(ErrorMessage.INVALID_BALANCE.getValue(), balance));
  }
}
