package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidHexValueException extends RuntimeException {

  public InvalidHexValueException(String value) {
    super(String.format(ErrorMessage.INVALID_HEX_VALUE.getValue(), value));
  }
}
