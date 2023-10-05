package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotDeriveKeysException extends RuntimeException {

  public CannotDeriveKeysException() {
    super(ErrorMessage.CANNOT_DERIVE_KEYS.getValue());
  }
}
