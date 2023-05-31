package software.crldev.multiversxspringbootstarterreactive.error.exception;

public class ProxyRequestException extends RuntimeException {

  public ProxyRequestException(String errorMessage) {
    super(errorMessage);
  }
}
