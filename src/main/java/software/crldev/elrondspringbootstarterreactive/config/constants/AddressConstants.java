package software.crldev.elrondspringbootstarterreactive.config.constants;

/**
 * Class containing static config values used in Address construction and validation
 *
 * @author carlo_stanciu
 */
public class AddressConstants {

    public static final String HRP = "erd";
    public static final int PUBKEY_LENGTH = 32;
    public static final int PUBKEY_HEX_LENGTH = PUBKEY_LENGTH * 2;
    public static final int BECH32_LENGTH = 62;
    public static final String ZERO_PUBKEY_STRING = "0000000000000000000000000000000000000000000000000000000000000000";

}
