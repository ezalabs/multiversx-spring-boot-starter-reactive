package software.crldev.multiversxspringbootstarterreactive.domain.esdt.common;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Value;
import org.bouncycastle.util.encoders.Hex;

/**
 * Value object for ESDT token property name
 *
 * @author carlo_stanciu
 */
@Value
@AllArgsConstructor
@EqualsAndHashCode(doNotUseGetters = true)
public class TokenProperty {

  TokenPropertyName name;

  @EqualsAndHashCode.Exclude
  Boolean value;

  /**
   * Getter
   *
   * @return - hex value of the property name
   */
  public String getNameHex() {
    return name.getHex();
  }

  /**
   * Getter
   *
   * @return - hex value of the property name
   */
  public String getValueHex() {
    return Hex.toHexString(value.toString().getBytes());
  }


}
