package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotCreateSignatureException extends RuntimeException {

  public CannotCreateSignatureException(String value) {
    super(String.format(ErrorMessage.CANNOT_CREATE_SIGNATURE.getValue(), value));
  }
}
