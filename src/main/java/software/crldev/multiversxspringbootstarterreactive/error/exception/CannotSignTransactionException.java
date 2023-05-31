package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotSignTransactionException extends RuntimeException {

  public CannotSignTransactionException(Throwable e) {
    super(String.format(ErrorMessage.CANNOT_SIGN_TRANSACTION.getValue(), e.getMessage()));
  }
}
