package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidTransactionStatusException extends RuntimeException {

  public InvalidTransactionStatusException(String status) {
    super(String.format(ErrorMessage.INVALID_BALANCE.getValue(), status));
  }
}
