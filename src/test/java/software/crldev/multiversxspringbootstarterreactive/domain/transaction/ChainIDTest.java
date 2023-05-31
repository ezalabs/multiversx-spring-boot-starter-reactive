package software.crldev.multiversxspringbootstarterreactive.domain.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.error.exception.InvalidChainIdException;

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