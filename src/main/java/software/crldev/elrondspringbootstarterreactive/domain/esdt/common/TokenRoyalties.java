package software.crldev.elrondspringbootstarterreactive.domain.esdt.common;

import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;
import software.crldev.elrondspringbootstarterreactive.util.HexValidator;

import static java.util.Objects.nonNull;

/**
 * Value object for ESDT NFT royalties
 *
 * @author carlo_stanciu
 */
@Value
public class TokenRoyalties {

    Integer value;

    private TokenRoyalties(Integer number) {
        this.value = number;
    }

    /**
     * Creates an TokenRoyalties object from number input
     *
     * @return - an instance of TokenRoyalties
     */
    public static TokenRoyalties fromNumber(Integer value) {
        if (!(nonNull(value) && value >= 0 && value <= 10000))
            throw new IllegalArgumentException(ErrorMessage.TOKEN_ROYALTIES.getValue());

        return new TokenRoyalties(value);
    }

    /**
     * Getter
     *
     * @return - hex value of the Token Royalties
     */
    public String getHex() {
        return HexValidator.processNumberHexArgument(Integer.toString(value, 16));
    }

    /**
     * Returns the value
     *
     * @return - value in String format
     */
    @Override
    public String toString() {
        return value.toString();
    }

}
