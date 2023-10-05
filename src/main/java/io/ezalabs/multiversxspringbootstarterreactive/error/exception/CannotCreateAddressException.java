package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotCreateAddressException extends RuntimeException {

  public CannotCreateAddressException(byte[] value) {
    super(String.format(ErrorMessage.CANNOT_CREATE_ADDRESS.getValue(), value));
  }
}
