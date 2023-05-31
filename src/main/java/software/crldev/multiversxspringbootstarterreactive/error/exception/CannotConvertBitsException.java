package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotConvertBitsException extends RuntimeException {

  public CannotConvertBitsException() {
    super(ErrorMessage.CANNOT_CONVERT_BITS.getValue());
  }
}
