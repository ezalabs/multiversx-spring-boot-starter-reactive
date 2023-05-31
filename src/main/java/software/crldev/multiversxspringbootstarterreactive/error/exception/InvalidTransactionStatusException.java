package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidTransactionStatusException extends RuntimeException {

  public InvalidTransactionStatusException(String status) {
    super(String.format(ErrorMessage.INVALID_BALANCE.getValue(), status));
  }
}
