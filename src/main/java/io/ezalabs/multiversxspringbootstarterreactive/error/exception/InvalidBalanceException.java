package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import java.math.BigInteger;
import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidBalanceException extends RuntimeException {

  public InvalidBalanceException(BigInteger balance) {
    super(String.format(ErrorMessage.INVALID_BALANCE.getValue(), balance));
  }
}
