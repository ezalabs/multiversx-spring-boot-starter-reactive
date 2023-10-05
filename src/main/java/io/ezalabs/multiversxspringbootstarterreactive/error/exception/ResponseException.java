package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class ResponseException extends RuntimeException {

  public ResponseException(String error) {
    super(error);
  }

  public ResponseException() {
    super(ErrorMessage.RESPONSE_NOT_SUCCESSFUL.getValue());
  }

}
