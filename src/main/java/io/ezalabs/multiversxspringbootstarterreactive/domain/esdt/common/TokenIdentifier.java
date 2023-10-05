package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;
import lombok.Value;
import org.bouncycastle.util.encoders.Hex;

/**
 * Value object for ESDT token identifier
 *
 * @author carlo_stanciu
 */
@Value
public class TokenIdentifier {

  String value;

  private TokenIdentifier(String name) {
    this.value = name;
  }

  /**
   * ¬ Creates a TokenIdentifier object from String input
   *
   * @return - an instance of TokenIdentifier
   */
  public static TokenIdentifier fromString(String name) {
    if (isNullOrEmpty(name)) {
      throw new IllegalArgumentException(ErrorMessage.TOKEN_IDENTIFIER.getValue());
    }

    var value = name.replaceAll("\\s+", "").toLowerCase();
    verifyFormat(value);

    return new TokenIdentifier(format("%s-%s",
        value.split("-")[0].toUpperCase(),
        value.split("-")[1].toLowerCase()));
  }

  /**
   * Getter
   *
   * @return - hex value of the TokenIdentifier
   */
  public String getHex() {
    return Hex.toHexString(value.getBytes());
  }

  /**
   * Returns the value
   *
   * @return - value in String format
   */
  @Override
  public String toString() {
    return value;
  }

  private static void verifyFormat(String value) {
    if (!(value.matches("^[a-zA-Z0-9]{3,10}+-[a-zA-Z0-9]{6}+$"))) {
      throw new IllegalArgumentException(ErrorMessage.TOKEN_IDENTIFIER.getValue());
    }
  }
}
