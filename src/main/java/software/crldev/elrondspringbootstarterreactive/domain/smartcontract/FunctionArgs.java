package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import lombok.Value;
import org.bouncycastle.util.encoders.Hex;
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;

/**
 * Value object for smart contract function arguments
 *
 * @author carlo_stanciu
 */
@Value
public class FunctionArgs {

    List<String> value;

    private FunctionArgs() {
        this.value = Collections.emptyList();
    }

    private FunctionArgs(String... args) {
        verifyArgs(args);
        this.value = List.of(args);
    }

    /**
     * Creates a FunctionArgs object from String array
     *
     * @param values - string values
     * @return - an instance of FunctionArgs
     */
    public static FunctionArgs fromString(String... values) {
        return new FunctionArgs(values);
    }

    /**
     * Creates a FunctionArgs object with empty array of args
     *
     * @return - an instance of FunctionArgs
     */
    public static FunctionArgs empty() {
        return new FunctionArgs();
    }

    /**
     * Checks if args list is empty
     *
     * @return boolean
     */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    /**
     * Getter
     *
     * @return - list of hex args value
     */
    public List<String> getHex() {
        return value.stream()
                .map(String::trim)
                .map(String::getBytes)
                .map(Hex::toHexString)
                .collect(Collectors.toList());
    }

    private void verifyArgs(String[] args) {
        if (args.length == 0 || hasEmptyOrNullArg(args))
            throw new IllegalArgumentException(ErrorMessage.FUNCTION_ARGS_NULL.getValue());

        Stream.of(args)
                .findAny()
                .map(a -> {
                    if (isNullOrEmpty(a)) {
                        throw new IllegalArgumentException(ErrorMessage.FUNCTION_ARGS_NULL.getValue());
                    }
                    return a;
                });
    }

    private boolean hasEmptyOrNullArg(String[] args) {
        for (var arg : args)
            if (isNullOrEmpty(arg))
                return true;

        return false;
    }

    /**
     * returns the list of args in String format
     *
     * @return - value string
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
