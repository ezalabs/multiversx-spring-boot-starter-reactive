package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Value;
import org.bouncycastle.util.encoders.Hex;
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

import java.math.BigInteger;

/**
 * Value object for ESDT token decimals
 *
 * @author carlo_stanciu
 */
@Value
public class InitialSupply {

    BigInteger supply;
    Integer decimals;
    String value;

    private InitialSupply(BigInteger supply, Integer decimals) {
        this.supply = supply;
        this.decimals = decimals;
        this.value = supply.toString() + "." + "0".repeat(Math.max(0, decimals));
    }

    /**
     * Creates an InitialSupply object from number inputs
     *
     * @return - an instance of InitialSupply
     */
    public static InitialSupply fromNumber(BigInteger supply, Integer decimals) {
        verifySupply(supply);
        verifyDecimals(decimals);

        return new InitialSupply(supply, decimals);
    }

    /**
     * Creates an InitialSupply object from String inputs
     *
     * @return - an instance of InitialSupply
     */
    public static InitialSupply fromString(String supply, String decimals) {
        var supplyNumber = new BigInteger(supply.strip());
        var decimalsNumber = Integer.parseInt(decimals.strip());
        verifySupply(supplyNumber);
        verifyDecimals(decimalsNumber);

        return fromNumber(supplyNumber, decimalsNumber);
    }

    /**
     * Getter
     *
     * @return - hex value of the initial supply
     */
    public String getSupplyHex() {
        return Hex.toHexString(supply.toString().getBytes());
    }

    /**
     * Getter
     *
     * @return - hex value of the decimals
     */
    public String getDecimalsHex() {
        return Hex.toHexString(decimals.toString().getBytes());
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

    private static void verifySupply(BigInteger supply) {
        if (!(supply.mod(BigInteger.TWO).equals(BigInteger.ZERO)))
            throw new IllegalArgumentException(ErrorMessage.INITIAL_SUPPLY.getValue());
    }

    private static void verifyDecimals(Integer decimals) {
        if (!(decimals >= 0 && decimals <= 18 && decimals % 2 == 0))
            throw new IllegalArgumentException(ErrorMessage.TOKEN_DECIMALS.getValue());
    }

}
