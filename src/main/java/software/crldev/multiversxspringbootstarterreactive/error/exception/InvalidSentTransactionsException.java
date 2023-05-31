package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidSentTransactionsException extends RuntimeException {

  public InvalidSentTransactionsException() {
    super(ErrorMessage.INVALID_SENT_TRANSACTIONS.getValue());
  }
}
