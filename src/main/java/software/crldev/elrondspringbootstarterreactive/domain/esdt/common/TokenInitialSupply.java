package software.crldev.elrondspringbootstarterreactive.domain.esdt.common;

import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;
import software.crldev.elrondspringbootstarterreactive.util.HexValidator;

import java.math.BigInteger;

/**
 * Value object for ESDT token initial supply
 *
 * @author carlo_stanciu
 */
@Value
public class TokenInitialSupply {

    BigInteger supply;

    private TokenInitialSupply(BigInteger supply) {
        this.supply = supply;
    }

    /**
     * Creates an InitialSupply object from number inputs
     *
     * @return - an instance of InitialSupply
     */
    public static TokenInitialSupply fromNumber(BigInteger supply) {
        verifySupply(supply);

        return new TokenInitialSupply(supply);
    }

    /**
     * Creates an InitialSupply object from String inputs
     *
     * @return - an instance of InitialSupply
     */
    public static TokenInitialSupply fromString(String supply) {
        var supplyNumber = new BigInteger(supply.strip());
        verifySupply(supplyNumber);

        return fromNumber(supplyNumber);
    }

    /**
     * Getter
     *
     * @return - hex value of the initial supply
     */
    public String getHex() {
        return HexValidator.processNumberHexArgument(supply.toString(16));
    }

    /**
     * Returns the value
     *
     * @return - value in String format
     */
    @Override
    public String toString() {
        return supply.toString();
    }

    private static void verifySupply(BigInteger supply) {
        if (supply.signum() == -1 || supply.signum() == 0)
            throw new IllegalArgumentException(ErrorMessage.INITIAL_SUPPLY.getValue());
    }

}
