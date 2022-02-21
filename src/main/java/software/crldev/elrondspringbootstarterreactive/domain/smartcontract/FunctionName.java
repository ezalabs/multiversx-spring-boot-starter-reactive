package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import lombok.Value;
import org.apache.logging.log4j.util.Strings;
import org.bouncycastle.util.encoders.Hex;
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;

/**
 * Value object for smart contract function name
 *
 * @author carlo_stanciu
 */
@Value
public class FunctionName {

    String value;

    private FunctionName(String name) {
        this.value = name;
    }

    /**
     * Creates a FunctionName object with empty array of args
     *
     * @return - an instance of FunctionName
     */
    public static FunctionName fromString(String name) {
        if (isNullOrEmpty(name))
            throw new IllegalArgumentException(ErrorMessage.FUNCTION_NAME.getValue());

        return new FunctionName(name.replaceAll("\\s+", ""));
    }

    /**
     * Creates a FunctionName object with empty array of args
     *
     * @return - an instance of FunctionName
     */
    public static FunctionName empty() {
        return new FunctionName(Strings.EMPTY);
    }

    /**
     * Checks if function name is empty
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    /**
     * Getter
     *
     * @return - hex value of the Function Name
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
}
