package io.ezalabs.multiversxspringbootstarterreactive.domain.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.ezalabs.multiversxspringbootstarterreactive.error.exception.InvalidChainIdException;
import org.junit.jupiter.api.Test;

class ChainIDTest {

  @Test
  void creation() {
    assertEquals(ChainID.DEVNET, ChainID.fromString("D"));
    assertEquals(ChainID.MAINNET, ChainID.fromString("1"));
    assertEquals(ChainID.TESTNET, ChainID.fromString("T"));
  }

  @Test
  void error() {
    assertThrows(InvalidChainIdException.class, () -> ChainID.fromString("unrecognized"));
  }
}