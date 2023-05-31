package software.crldev.multiversxspringbootstarterreactive.domain.account;

import static software.crldev.multiversxspringbootstarterreactive.config.constants.AddressConstants.BECH32_LENGTH;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.AddressConstants.HRP;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.AddressConstants.PUBKEY_HEX_LENGTH;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.AddressConstants.ZERO_PUBKEY_STRING;
import static software.crldev.multiversxspringbootstarterreactive.util.BitsConverter.convertBits;

import org.bitcoinj.core.Bech32;
import org.bouncycastle.util.encoders.Hex;
import software.crldev.multiversxspringbootstarterreactive.error.exception.CannotDecodeBech32AddressException;
import software.crldev.multiversxspringbootstarterreactive.error.exception.InvalidHexValueException;
import software.crldev.multiversxspringbootstarterreactive.error.exception.WrongNetworkAddressException;
import software.crldev.multiversxspringbootstarterreactive.util.HexValidator;

/**
 * Domain object for Address representation
 *
 * @author carlo_stanciu
 */
public class Address {

  private final String hexValue;

  private Address(String hexValue) {
    this.hexValue = hexValue;
  }

  /**
   * Method used for creating an Address using a public key
   *
   * @param hexValue - public key in hex representation
   * @return - an instance of Address
   */
  public static Address fromHex(String hexValue) {
    if (!(HexValidator.isHexValid(hexValue, PUBKEY_HEX_LENGTH))) {
      throw new InvalidHexValueException(hexValue);
    }

    return new Address(hexValue);
  }

  /**
   * Method used for creating an Address using a bech32 address
   *
   * @param bech32Value - address String value in bech32 representation
   * @return - an instance of Address
   */
  public static Address fromBech32(String bech32Value) {
    Bech32.Bech32Data bech32Data;

    try {
      bech32Data = Bech32.decode(bech32Value);
    } catch (Exception e) {
      throw new CannotDecodeBech32AddressException();
    }

    if (!bech32Data.hrp.equals(HRP)) {
      throw new WrongNetworkAddressException(HRP);
    }

    var decodedBytes = convertBits(bech32Data.data, 5, 8, false);

    return new Address(new String(Hex.encode(decodedBytes)));
  }

  /**
   * Getter
   *
   * @return - hex value of the Address
   */
  public String getHex() {
    return hexValue;
  }

  /**
   * Getter
   *
   * @return - public key buffer of the Address
   */
  public byte[] getPublicKey() {
    return Hex.decode(hexValue);
  }

  /**
   * Getter
   *
   * @return - bech32 value of the Address
   */
  public String getBech32() {
    var bech32Value = Bech32.encode(HRP, convertBits(getPublicKey(), 8, 5, true));
    if (bech32Value.length() != BECH32_LENGTH) {
      throw new CannotDecodeBech32AddressException();
    }

    return bech32Value;
  }

  /**
   * Creates an Address with zero public key String
   *
   * @return - an instance of Address
   */
  public static Address zero() {
    return new Address(ZERO_PUBKEY_STRING);
  }

  /**
   * Checks if Address is zero
   *
   * @return boolean
   */
  public boolean isZero() {
    return hexValue.equals(ZERO_PUBKEY_STRING);
  }

}
