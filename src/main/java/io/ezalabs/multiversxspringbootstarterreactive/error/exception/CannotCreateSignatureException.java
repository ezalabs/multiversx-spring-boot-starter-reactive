package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotCreateSignatureException extends RuntimeException {

  public CannotCreateSignatureException(String value) {
    super(String.format(ErrorMessage.CANNOT_CREATE_SIGNATURE.getValue(), value));
  }
}
