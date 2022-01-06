package software.crldev.elrondspringbootstarterreactive.util;

import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.config.ErdNetworkConfigSupplier;

import java.math.BigInteger;

/**
 * Utility class for Gas
 *
 * @author carlo_stanciu
 */
public class GasUtils {

    /**
     * Method used for computing gas cost of a Transaction based on payload data
     *
     * @param data - data to process
     * @return - gas cost in BigInteger format
     */
    public static BigInteger computeGasCost(PayloadData data) {
        return ErdNetworkConfigSupplier.config.getMinGasLimit()
                .add(BigInteger.valueOf(data.toString().length())
                        .multiply(BigInteger.valueOf(ErdNetworkConfigSupplier.config.getGasPerDataByte())));
    }

}
