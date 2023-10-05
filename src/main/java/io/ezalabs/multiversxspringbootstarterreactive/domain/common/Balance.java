package io.ezalabs.multiversxspringbootstarterreactive.domain.common;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.CurrencyConstants;
import io.ezalabs.multiversxspringbootstarterreactive.error.exception.InvalidBalanceException;
import io.ezalabs.multiversxspringbootstarterreactive.util.HexValidator;
import java.math.BigDecimal;
import java.math.BigInteger;
import lombok.Value;

/**
 * Value object for Balance
 *
 * @author carlo_stanciu
 */
@Value
public class Balance {

  BigInteger value;

  private Balance(String value) {
    var bigIntValue = new BigInteger(value);

    if (bigIntValue.signum() == -1) {
      throw new InvalidBalanceException(bigIntValue);
    }

    this.value = bigIntValue;
  }

  /**
   * Creates an Balance object from a String representing EGLD (without denomination)
   *
   * @param value - EGLD String value
   * @return - an instance of Balance
   */
  public static Balance fromEgld(Double value) {
    var oneEgldString = CurrencyConstants.ONE_EGLD_STRING;

    var bigDecimalGold = new BigDecimal(String.valueOf(value));
    var unit = bigDecimalGold.intValue();
    var decimals = bigDecimalGold.subtract(new BigDecimal(unit))
        .toPlainString().split("\\.")[1];

    var bigIntUnits = new BigInteger(unit + decimals)
        .multiply(new BigInteger(oneEgldString.substring(0, oneEgldString.length() - decimals.length())))
        .toString();

    return new Balance(bigIntUnits);
  }

  /**
   * Getter
   *
   * @return - hex value of the balance
   */
  public String getHex() {
    return HexValidator.processNumberHexArgument(value.toString(16));
  }

  /**
   * Creates a Balance object from a String representing EGLD (with denomination)
   *
   * @param value - EGLD String value
   * @return - an instance of Balance
   */
  public static Balance fromString(String value) {
    return new Balance(value);
  }

  /**
   * Creates an Balance object from a number value
   *
   * @param value - value in BigInteger format
   * @return an instance of Balance
   */
  public static Balance fromNumber(BigInteger value) {
    return new Balance(value.toString());
  }

  /**
   * Creates a Balance object with value 0
   *
   * @return - an instance of Balance
   */
  public static Balance zero() {
    return new Balance("0");
  }

  /**
   * Checks if Balance is zero
   *
   * @return boolean
   */
  public boolean isZero() {
    return value.signum() == 0;
  }

  /**
   * Checks if Balance has been set
   *
   * @return boolean
   */
  public boolean isSet() {
    return value.signum() != 0;
  }

  /**
   * @return EGLD in String format with 2 decimal points
   */
  public String toCurrencyString() {
    var currencyValues = value.divideAndRemainder(new BigInteger(CurrencyConstants.ONE_EGLD_STRING));
    var number = currencyValues[0].toString();
    var decimals = currencyValues[1].toString();

    var decimalsToDisplay = decimals.equals("0") ?
        decimals.concat("0") : currencyValues[1].toString().substring(0, 2);

    return String.format("%s.%s%s", number, decimalsToDisplay, CurrencyConstants.EGLD_TICKER);
  }

  /**
   * Returns a String representation of Balance in BASE10
   *
   * @return - String in BASE10
   */
  @Override
  public String toString() {
    return value.toString(CurrencyConstants.BASE10);
  }

}
