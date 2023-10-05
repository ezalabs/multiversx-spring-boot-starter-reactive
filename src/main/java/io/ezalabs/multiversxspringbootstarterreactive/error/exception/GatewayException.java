package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class GatewayException extends RuntimeException {

  public GatewayException() {
    super(ErrorMessage.INVALID_GATEWAY.getValue());
  }
}
