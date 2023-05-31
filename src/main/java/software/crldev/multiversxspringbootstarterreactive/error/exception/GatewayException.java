package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class GatewayException extends RuntimeException {

  public GatewayException() {
    super(ErrorMessage.INVALID_GATEWAY.getValue());
  }
}
