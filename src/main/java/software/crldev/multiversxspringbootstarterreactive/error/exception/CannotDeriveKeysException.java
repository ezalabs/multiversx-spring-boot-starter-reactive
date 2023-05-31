package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotDeriveKeysException extends RuntimeException {

  public CannotDeriveKeysException() {
    super(ErrorMessage.CANNOT_DERIVE_KEYS.getValue());
  }
}
