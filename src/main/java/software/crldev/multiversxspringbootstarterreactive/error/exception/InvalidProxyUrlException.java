package software.crldev.multiversxspringbootstarterreactive.error.exception;

import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidProxyUrlException extends RuntimeException {

  public InvalidProxyUrlException(String url) {
    super(ErrorMessage.INVALID_PROXY_URL.getValue().formatted(url));
  }
}
