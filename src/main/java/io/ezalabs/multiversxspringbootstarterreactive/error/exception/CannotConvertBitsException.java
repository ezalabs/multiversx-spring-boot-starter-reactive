package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotConvertBitsException extends RuntimeException {

  public CannotConvertBitsException() {
    super(ErrorMessage.CANNOT_CONVERT_BITS.getValue());
  }
}
