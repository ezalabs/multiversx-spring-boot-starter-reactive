package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotGenerateMnemonicException extends RuntimeException {

  public CannotGenerateMnemonicException() {
    super(ErrorMessage.CANNOT_GENERATE_MNEMONIC.getValue());
  }
}
