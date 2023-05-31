package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class MissingTransactionRequestException extends RuntimeException {

  public MissingTransactionRequestException() {
    super(ErrorMessage.MISSING_TRANSACTIONS_REQUESTS.getValue());
  }
}
