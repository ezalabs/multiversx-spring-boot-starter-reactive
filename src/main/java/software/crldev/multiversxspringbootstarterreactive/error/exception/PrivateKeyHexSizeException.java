package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class PrivateKeyHexSizeException extends RuntimeException {

  public PrivateKeyHexSizeException(int size) {
    super(String.format(ErrorMessage.PRIVATE_KEY_LENGTH.getValue(), size));
  }
}
