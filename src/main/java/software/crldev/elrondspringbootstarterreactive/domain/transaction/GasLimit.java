package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import software.crldev.elrondspringbootstarterreactive.error.exception.NegativeGasException;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.config.CurrencyConstants;

import java.math.BigInteger;

/**
 * Value object for GasLimit
 *
 * @author carlo_stanciu
 */
@Value
public class GasLimit {

    BigInteger value;

    private GasLimit(BigInteger value) {
        if (value.signum() == -1)
            throw new NegativeGasException(value);

        this.value = value;
    }

    /**
     * Creates an GasLimit object from
     * a number value
     *
     * @param value - value in BigInteger format
     * @return an instance of GasLimit
     */
    public static GasLimit fromNumber(BigInteger value) {
        return new GasLimit(value);
    }

    /**
     * Creates an GasLimit object from
     * a String value
     *
     * @param value - value in String format
     * @return an instance of GasLimit
     */
    public static GasLimit fromString(String value) {
        return fromNumber(new BigInteger(value));
    }

    @Override
    public String toString() {
        return value.toString(CurrencyConstants.BASE10);
    }

}
