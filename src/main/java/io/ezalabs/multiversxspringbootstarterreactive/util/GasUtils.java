package io.ezalabs.multiversxspringbootstarterreactive.util;

import io.ezalabs.multiversxspringbootstarterreactive.config.MxNetworkConfigSupplier;
import java.math.BigInteger;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;

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
    return MxNetworkConfigSupplier.config.getMinGasLimit()
        .add(BigInteger.valueOf(data.length())
            .multiply(BigInteger.valueOf(MxNetworkConfigSupplier.config.getGasPerDataByte())));
  }

}
