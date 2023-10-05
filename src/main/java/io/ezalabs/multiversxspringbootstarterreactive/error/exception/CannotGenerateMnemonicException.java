package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotGenerateMnemonicException extends RuntimeException {

  public CannotGenerateMnemonicException() {
    super(ErrorMessage.CANNOT_GENERATE_MNEMONIC.getValue());
  }
}
