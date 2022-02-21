package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.config.constants.CurrencyConstants;
import software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants;
import software.crldev.elrondspringbootstarterreactive.config.constants.TransactionConstants;
import software.crldev.elrondspringbootstarterreactive.error.exception.NegativeGasException;

import java.math.BigInteger;

import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_MULTI_TRANSFER_GAS_COST_BASE;

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

    /**
     * Creates a GasLimit object with value 0
     *
     * @return - an instance of GasLimit
     */
    public static GasLimit zero() {
        return new GasLimit(BigInteger.ZERO);
    }

    /**
     * Checks if GasLimit is zero
     *
     * @return boolean
     */
    public boolean isZero() {
        return value.signum() == 0;
    }

    /**
     * Creates a GasLimit object with default SC call value
     *
     * @return - an instance of GasLimit
     */
    public static GasLimit defaultSmartContractCall() {
        return fromNumber(TransactionConstants.SC_CALL_GAS_LIMIT);
    }

    /**
     * Creates a GasLimit object with default ESDT transfer value
     *
     * @return - an instance of GasLimit
     */
    public static GasLimit defaultEsdtTransfer() {
        return fromNumber(ESDTConstants.ESDT_TRANSFER_GAS_COST);
    }

    /**
     * Creates a GasLimit object with multi ESDT transfer value
     *
     * @param numOfTokens - num of tokens for base multiplier
     * @return - an instance of GasLimit
     */
    public static GasLimit multiEsdtTransfer(int numOfTokens) {
        return fromNumber(ESDT_MULTI_TRANSFER_GAS_COST_BASE
                .multiply(BigInteger.valueOf(numOfTokens)));
    }

    /**
     * Creates a GasLimit object with default ESDT issuance value
     *
     * @return - an instance of GasLimit
     */
    public static GasLimit defaultEsdtIssuance() {
        return fromNumber(ESDTConstants.ESDT_ISSUANCE_GAS_COST);
    }

    /**
     * Creates a GasLimit object with default NFT create value
     *
     * @return - an instance of GasLimit
     */
    public static GasLimit defaultNftCreate() {
        return fromNumber(ESDTConstants.ESDT_NFT_CREATE_COST);
    }

    /**
     * Creates a GasLimit object with default ESDT minting value
     *
     * @return - an instance of GasLimit
     */
    public static GasLimit defaultEsdtLocalOp() {
        return fromNumber(ESDTConstants.ESDT_LOCAL_OP_GAS_COST);
    }

    /**
     * Creates a GasLimit object with default ESDT minting value
     *
     * @return - an instance of GasLimit
     */
    public static GasLimit defaultEsdtGlobalOp() {
        return fromNumber(ESDTConstants.ESDT_GLOBAL_OP_GAS_COST);
    }

    @Override
    public String toString() {
        return value.toString(CurrencyConstants.BASE10);
    }

}
