package io.ezalabs.multiversxspringbootstarterreactive.domain.common;

import io.ezalabs.multiversxspringbootstarterreactive.error.exception.InvalidNonceException;
import io.ezalabs.multiversxspringbootstarterreactive.util.HexValidator;
import lombok.Value;

/**
 * Value object for Nonce
 *
 * @author carlo_stanciu
 */
@Value
public class Nonce {

  Long value;

  private Nonce(Long value) {
    if (value < 0) {
      throw new InvalidNonceException(value);
    }

    this.value = value;
  }

  /**
   * Creates Nonce from Long
   *
   * @param value - Long value for nonce
   * @return - an instance of Nonce
   */
  public static Nonce fromLong(Long value) {
    return new Nonce(value);
  }

  /**
   * Creates a Nonce object with value 0
   *
   * @return - an instance of Nonce
   */
  public static Nonce zero() {
    return new Nonce(0L);
  }

  /**
   * Getter
   *
   * @return - hex value of the nonce
   */
  public String getHex() {
    return HexValidator.processNumberHexArgument(Long.toString(value, 16));
  }

  @Override
  public String toString() {
    return value.toString();
  }

}
