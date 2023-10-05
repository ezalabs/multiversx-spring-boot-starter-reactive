package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class WrongNetworkAddressException extends RuntimeException {

  public WrongNetworkAddressException(String hrp) {
    super(String.format(ErrorMessage.WRONG_NETWORK_ADDRESS.getValue(), hrp));
  }
}
