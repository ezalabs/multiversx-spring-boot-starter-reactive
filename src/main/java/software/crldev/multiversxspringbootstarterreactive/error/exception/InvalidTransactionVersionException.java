package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidTransactionVersionException extends RuntimeException {

  public InvalidTransactionVersionException(int transactionId) {
    super(String.format(ErrorMessage.INVALID_TRANSACTION_VERSION.getValue(), transactionId));
  }
}
