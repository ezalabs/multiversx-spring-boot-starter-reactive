package com.crldev.elrondspringbootstarterreactive.domain.transaction;

import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Hash;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HashTest {

    byte[] hashAsBuffer = "092309uf230j9j03f29".getBytes();

    @Test
    void fromString() {
        var hashAsString = new String(Hex.encode(hashAsBuffer));
        var expected = Hex.decode(hashAsString);

        assertArrayEquals(expected, Hash.fromString(hashAsString).getValue());
    }

    @Test
    void fromBuffer() {
        assertArrayEquals(hashAsBuffer, Hash.fromBuffer(hashAsBuffer).getValue());
    }

}