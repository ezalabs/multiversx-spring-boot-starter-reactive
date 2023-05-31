package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class ResponseException extends RuntimeException {

  public ResponseException(String error) {
    super(error);
  }

  public ResponseException() {
    super(ErrorMessage.RESPONSE_NOT_SUCCESSFUL.getValue());
  }

}
