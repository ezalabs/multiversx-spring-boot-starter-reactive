package software.crldev.elrondspringbootstarterreactive.config.constants;

import java.math.BigInteger;

/**
 * Class containing static config values used in Balance construction and validation
 *
 * @author carlo_stanciu
 */
public class CurrencyConstants {

    public static final Integer BASE10 = 10;
    public static final Integer DENOMINATION = 18;
    public static final String ONE_EGLD_STRING = BigInteger.ONE.multiply(BigInteger.TEN.pow(DENOMINATION)).toString();
    public static final String EGLD_TICKER = "EGLD";

}
