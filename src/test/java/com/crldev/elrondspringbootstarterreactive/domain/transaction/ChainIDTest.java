package com.crldev.elrondspringbootstarterreactive.domain.transaction;

import software.crldev.elrondspringbootstarterreactive.domain.transaction.ChainID;
import software.crldev.elrondspringbootstarterreactive.error.exception.InvalidChainIdException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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