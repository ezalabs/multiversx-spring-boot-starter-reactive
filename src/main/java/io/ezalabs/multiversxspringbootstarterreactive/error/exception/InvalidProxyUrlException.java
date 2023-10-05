package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class InvalidProxyUrlException extends RuntimeException {

  public InvalidProxyUrlException(String url) {
    super(ErrorMessage.INVALID_PROXY_URL.getValue().formatted(url));
  }
}
