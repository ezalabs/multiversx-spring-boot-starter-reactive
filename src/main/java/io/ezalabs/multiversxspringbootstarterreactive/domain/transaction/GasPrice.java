package io.ezalabs.multiversxspringbootstarterreactive.domain.transaction;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.CurrencyConstants;
import io.ezalabs.multiversxspringbootstarterreactive.error.exception.NegativeGasException;
import java.math.BigInteger;
import lombok.Value;

/**
 * Value object for GasPrice
 *
 * @author carlo_stanciu
 */
@Value
public class GasPrice {

  BigInteger value;

  private GasPrice(BigInteger value) {
    if (value.signum() == -1) {
      throw new NegativeGasException(value);
    }

    this.value = value;
  }

  /**
   * Creates an GasPrice object from a number value
   *
   * @param value - value in BigInteger format
   * @return an instance of GasPrice
   */
  public static GasPrice fromNumber(BigInteger value) {
    return new GasPrice(value);
  }

  /**
   * Creates an GasPrice object from a String value
   *
   * @param value - value in String format
   * @return an instance of GasPrice
   */
  public static GasPrice fromString(String value) {
    return fromNumber(new BigInteger(value));
  }

  @Override
  public String toString() {
    return value.toString(CurrencyConstants.BASE10);
  }

}
