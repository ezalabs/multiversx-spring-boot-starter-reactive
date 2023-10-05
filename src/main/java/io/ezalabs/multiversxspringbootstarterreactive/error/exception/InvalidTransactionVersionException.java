package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidTransactionVersionException extends RuntimeException {

  public InvalidTransactionVersionException(int transactionId) {
    super(String.format(ErrorMessage.INVALID_TRANSACTION_VERSION.getValue(), transactionId));
  }
}
