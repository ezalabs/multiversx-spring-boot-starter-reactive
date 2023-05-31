package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidNonceException extends RuntimeException {

  public InvalidNonceException(long nonce) {
    super(String.format(ErrorMessage.INVALID_NONCE.getValue(), nonce));
  }
}
