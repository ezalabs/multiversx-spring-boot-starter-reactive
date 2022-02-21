package software.crldev.elrondspringbootstarterreactive.domain.esdt.common;

import lombok.Value;
import org.bouncycastle.util.encoders.Hex;
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;

/**
 * Value object for ESDT token ticker
 *
 * @author carlo_stanciu
 */
@Value
public class TokenTicker {

    String value;

    private TokenTicker(String name) {
        this.value = name;
    }

    /**
     * ¬
     * Creates a TokenTicker object from String input
     *
     * @return - an instance of TokenTicker
     */
    public static TokenTicker fromString(String name) {
        if (isNullOrEmpty(name))
            throw new IllegalArgumentException(ErrorMessage.TOKEN_NAME.getValue());

        var value = name.replaceAll("\\s+", "").toUpperCase();
        verifyFormat(value);

        return new TokenTicker(value);
    }

    /**
     * Getter
     *
     * @return - hex value of the Token Ticker
     */
    public String getHex() {
        return Hex.toHexString(value.getBytes());
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

    private static void verifyFormat(String value) {
        if (!value.matches("^[a-zA-Z0-9]{3,10}+$"))
            throw new IllegalArgumentException(ErrorMessage.TOKEN_TICKER.getValue());
    }
}
