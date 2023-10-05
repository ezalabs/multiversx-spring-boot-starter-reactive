package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class SignatureEmptyException extends RuntimeException {

  public SignatureEmptyException() {
    super(ErrorMessage.SIGNATURE_EMPTY.getValue());
  }
}
