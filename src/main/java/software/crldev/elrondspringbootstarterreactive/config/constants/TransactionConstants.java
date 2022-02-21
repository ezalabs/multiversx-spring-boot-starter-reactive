package software.crldev.elrondspringbootstarterreactive.config.constants;

import java.math.BigInteger;

/**
 * Class containing static config values used in Transaction construction and validation
 *
 * @author carlo_stanciu
 */
public class TransactionConstants {

    public static final Integer TRANSACTION_VERSION_DEFAULT = 1;
    public static final Integer TRANSACTION_VERSION_TX_HASH_SIGN = 2;
    public static final Integer SIGNATURE_LENGTH = 64;
    public static final Integer SIGNATURE_LENGTH_HEX = SIGNATURE_LENGTH * 2;
    public static final BigInteger SC_CALL_GAS_LIMIT = BigInteger.valueOf(20_000_000L);

}
