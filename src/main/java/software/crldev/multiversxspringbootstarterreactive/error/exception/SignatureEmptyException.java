package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class SignatureEmptyException extends RuntimeException {

  public SignatureEmptyException() {
    super(ErrorMessage.SIGNATURE_EMPTY.getValue());
  }
}
