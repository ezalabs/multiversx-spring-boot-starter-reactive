package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class EmptyPayloadException extends RuntimeException {

  public EmptyPayloadException() {
    super(ErrorMessage.PAYLOAD.getValue());
  }
}
