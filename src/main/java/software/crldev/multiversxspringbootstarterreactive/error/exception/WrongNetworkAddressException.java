package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class WrongNetworkAddressException extends RuntimeException {

  public WrongNetworkAddressException(String hrp) {
    super(String.format(ErrorMessage.WRONG_NETWORK_ADDRESS.getValue(), hrp));
  }
}
