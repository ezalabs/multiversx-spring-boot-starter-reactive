package software.crldev.multiversxspringbootstarterreactive.error.exception;

import static java.lang.String.format;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidPemFileException extends RuntimeException {

  public InvalidPemFileException(Throwable t) {
    super(format(ErrorMessage.INVALID_PEM_FILE.getValue(), t.getMessage()));
  }
}
