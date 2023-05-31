package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class CannotDecodeBech32AddressException extends RuntimeException {

  public CannotDecodeBech32AddressException() {
    super(ErrorMessage.CANNOT_DECODE_BECH32_ADDRESS.getValue());
  }
}
