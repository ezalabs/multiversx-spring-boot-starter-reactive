package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotSignTransactionException extends RuntimeException {

  public CannotSignTransactionException(Throwable e) {
    super(String.format(ErrorMessage.CANNOT_SIGN_TRANSACTION.getValue(), e.getMessage()));
  }
}
