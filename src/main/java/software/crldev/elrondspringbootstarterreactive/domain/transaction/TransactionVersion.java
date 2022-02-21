package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import software.crldev.elrondspringbootstarterreactive.error.exception.InvalidTransactionVersionException;
import lombok.Value;

import static software.crldev.elrondspringbootstarterreactive.config.constants.TransactionConstants.TRANSACTION_VERSION_DEFAULT;
import static software.crldev.elrondspringbootstarterreactive.config.constants.TransactionConstants.TRANSACTION_VERSION_TX_HASH_SIGN;

/**
 * Value object for TransactionVersion
 *
 * @author carlo_stanciu
 */
@Value
public class TransactionVersion {

    Integer value;

    private TransactionVersion(Integer value) {
        if (Integer.signum(value) == -1) {
            throw new InvalidTransactionVersionException(value);
        }

        this.value = value;
    }

    /**
     * Creates an TransactionVersion object from
     * a number value
     *
     * @param value - value in Integer format
     * @return an instance of TransactionVersion
     */
    public static TransactionVersion fromInteger(Integer value) {
        return new TransactionVersion(value);
    }

    /**
     * Create an TransactionVersion object with default version
     *
     * @return an instance of TransactionVersion
     */
    public static TransactionVersion withDefaultVersion() {
        return new TransactionVersion(TRANSACTION_VERSION_DEFAULT);
    }

    /**
     * Create an TransactionVersion object with transaction hash sign version
     *
     * @return an instance of TransactionVersion
     */
    public static TransactionVersion withTransactionHashSignVersion() {
        return new TransactionVersion(TRANSACTION_VERSION_TX_HASH_SIGN);
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
