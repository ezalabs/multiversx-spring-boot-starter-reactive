package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidSentTransactionsException extends RuntimeException {

  public InvalidSentTransactionsException() {
    super(ErrorMessage.INVALID_SENT_TRANSACTIONS.getValue());
  }
}
