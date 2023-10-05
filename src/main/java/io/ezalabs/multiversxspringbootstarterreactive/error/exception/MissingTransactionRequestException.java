package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class MissingTransactionRequestException extends RuntimeException {

  public MissingTransactionRequestException() {
    super(ErrorMessage.MISSING_TRANSACTIONS_REQUESTS.getValue());
  }
}
