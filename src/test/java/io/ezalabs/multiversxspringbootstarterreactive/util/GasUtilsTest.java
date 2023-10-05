package io.ezalabs.multiversxspringbootstarterreactive.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;

class GasUtilsTest {

  @Test
  void computeGasCost() {
    var expectedCost = BigInteger.valueOf(92500);
    assertEquals(expectedCost, GasUtils.computeGasCost(PayloadData.fromString("crldev x elrond")));

    var expectedCostEmpty = BigInteger.valueOf(70000);
    assertEquals(expectedCostEmpty, GasUtils.computeGasCost(PayloadData.empty()));
  }

}