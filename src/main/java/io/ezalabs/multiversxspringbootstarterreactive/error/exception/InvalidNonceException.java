package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidNonceException extends RuntimeException {

  public InvalidNonceException(long nonce) {
    super(String.format(ErrorMessage.INVALID_NONCE.getValue(), nonce));
  }
}
