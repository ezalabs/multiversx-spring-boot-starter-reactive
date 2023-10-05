package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class EmptyPayloadException extends RuntimeException {

  public EmptyPayloadException() {
    super(ErrorMessage.PAYLOAD.getValue());
  }
}
