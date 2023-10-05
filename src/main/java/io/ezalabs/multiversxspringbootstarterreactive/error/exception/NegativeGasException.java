package io.ezalabs.multiversxspringbootstarterreactive.error.exception;

import java.math.BigInteger;
import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;

public class NegativeGasException extends RuntimeException {

  public NegativeGasException(BigInteger gasLimit) {
    super(String.format(ErrorMessage.NEGATIVE_GAS.getValue(), gasLimit));
  }
}
