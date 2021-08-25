package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import software.crldev.elrondspringbootstarterreactive.error.exception.InvalidChainIdException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Locale;

/**
 * Enum representing Chain ID values
 *
 * @author carlo_stanciu
 */
@AllArgsConstructor
@Getter
public enum ChainID {

    MAINNET("1"),
    DEVNET("D"),
    TESTNET("T");

    private final String value;

    /**
     * Creates enum instance from String input
     *
     * @param id - String value of chainID
     * @return - ChainID instance
     */
    public static ChainID fromString(String id) {
        var value = id.toUpperCase(Locale.ROOT);

        switch (value) {
            case "1":
                return ChainID.MAINNET;
            case "D":
                return ChainID.DEVNET;
            case "T":
                return ChainID.TESTNET;
            default:
                throw new InvalidChainIdException(id);
        }
    }


}
