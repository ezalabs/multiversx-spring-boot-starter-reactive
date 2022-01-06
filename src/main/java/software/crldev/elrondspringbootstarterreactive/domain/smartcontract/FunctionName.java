package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.elrondspringbootstarterreactive.error.exception.FunctionNameException;

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
     * Creates a FunctionArgs object with empty array of args
     *
     * @return - an instance of FunctionName
     */
    public static FunctionName fromString(String name) {
        if (isNullOrEmpty(name))
            throw new FunctionNameException();

        return new FunctionName(name.trim());
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
