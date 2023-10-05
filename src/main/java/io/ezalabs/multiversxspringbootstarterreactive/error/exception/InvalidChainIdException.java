package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidChainIdException extends RuntimeException {

  public InvalidChainIdException(String chainId) {
    super(String.format(ErrorMessage.INVALID_CHAIN_ID.getValue(), chainId));
  }
}
