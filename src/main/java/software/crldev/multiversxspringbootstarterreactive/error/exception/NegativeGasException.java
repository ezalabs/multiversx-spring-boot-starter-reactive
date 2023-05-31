package software.crldev.multiversxspringbootstarterreactive.error.exception;

import java.math.BigInteger;
import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

public class NegativeGasException extends RuntimeException {

  public NegativeGasException(BigInteger gasLimit) {
    super(String.format(ErrorMessage.NEGATIVE_GAS.getValue(), gasLimit));
  }
}
