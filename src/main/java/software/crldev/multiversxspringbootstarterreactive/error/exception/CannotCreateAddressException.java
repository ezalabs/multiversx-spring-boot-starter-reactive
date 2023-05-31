package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotCreateAddressException extends RuntimeException {

  public CannotCreateAddressException(byte[] value) {
    super(String.format(ErrorMessage.CANNOT_CREATE_ADDRESS.getValue(), value));
  }
}
