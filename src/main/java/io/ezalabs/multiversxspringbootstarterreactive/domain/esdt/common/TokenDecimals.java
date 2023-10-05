package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;
import io.ezalabs.multiversxspringbootstarterreactive.util.HexValidator;
import lombok.Value;

/**
 * Value object for ESDT token decimals
 *
 * @author carlo_stanciu
 */
@Value
public class TokenDecimals {

  Integer decimals;

  private TokenDecimals(Integer decimals) {
    this.decimals = decimals;
  }

  /**
   * Creates an TokenDecimals object from number input
   *
   * @return - an instance of TokenDecimals
   */
  public static TokenDecimals fromNumber(Integer decimals) {
    verifyDecimals(decimals);

    return new TokenDecimals(decimals);
  }

  /**
   * Creates an TokenDecimals object from String inputs
   *
   * @return - an instance of TokenDecimals
   */
  public static TokenDecimals fromString(String decimals) {
    var decimalsNumber = Integer.parseInt(decimals.strip());
    verifyDecimals(decimalsNumber);

    return fromNumber(decimalsNumber);
  }

  /**
   * Getter
   *
   * @return - hex value of the decimals
   */
  public String getHex() {
    return HexValidator.processNumberHexArgument(Integer.toString(decimals, 16));
  }

  /**
   * Returns the value
   *
   * @return - value in String format
   */
  @Override
  public String toString() {
    return decimals.toString();
  }

  private static void verifyDecimals(Integer decimals) {
    if (!(decimals >= 0 && decimals <= 18 && decimals % 2 == 0)) {
      throw new IllegalArgumentException(ErrorMessage.TOKEN_DECIMALS.getValue());
    }
  }

}
