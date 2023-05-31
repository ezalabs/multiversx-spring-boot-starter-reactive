package software.crldev.multiversxspringbootstarterreactive.domain.smartcontract;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;
import static java.util.Objects.nonNull;

import java.math.BigInteger;
import lombok.Value;
import org.bouncycastle.util.encoders.Hex;
import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;
import software.crldev.multiversxspringbootstarterreactive.util.HexValidator;

/**
 * Value object for smart contract function argument
 *
 * @author carlo_stanciu
 */
@Value
public class FunctionArg {

  String sVal;
  BigInteger nVal;

  private FunctionArg(String sVal, BigInteger nVal) {
    this.sVal = sVal;
    this.nVal = nVal;
  }

  /**
   * Creates a FunctionArg object from String input
   *
   * @param arg - string arg
   * @return - an instance of FunctionArg
   */
  public static FunctionArg fromString(String arg) {
    if (isNullOrEmpty(arg)) {
      throw new IllegalArgumentException(ErrorMessage.FUNCTION_ARG_NULL.getValue());
    }
    return new FunctionArg(arg.trim(), null);
  }

  /**
   * Creates a FunctionArgs object from BigInteger input
   *
   * @param arg - number arg
   * @return - an instance of FunctionArg
   */
  public static FunctionArg fromNumber(BigInteger arg) {
    if ((!nonNull(arg)) || arg.signum() == -1) {
      throw new IllegalArgumentException(ErrorMessage.FUNCTION_ARG_NULL.getValue());
    }
    return new FunctionArg(null, arg);
  }

  /**
   * Getter
   *
   * @return - hex value
   */
  public String getHex() {
    return nonNull(sVal) ?
        Hex.toHexString(sVal.getBytes())
        :
            HexValidator.processNumberHexArgument(nVal.toString(16));
  }

  /**
   * returns the arg in String format
   *
   * @return - value string
   */
  @Override
  public String toString() {
    return nonNull(sVal) ? sVal : nVal.toString();
  }
}
