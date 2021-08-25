package com.crldev.elrondspringbootstarterreactive.domain.account;

import software.crldev.elrondspringbootstarterreactive.config.AddressConstants;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.error.exception.CannotDecodeBech32AddressException;
import software.crldev.elrondspringbootstarterreactive.error.exception.InvalidHexValueException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    String hexPublicKey = "bf86ad970aa3c93c2382889c0a4fba4728b5b851634d57ccd65dcddeda8c8bb0";
    String bech32 = "erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj";

    @Test
    void fromHex() {
        assertDoesNotThrow(() -> Address.fromHex(hexPublicKey));
        assertThrows(InvalidHexValueException.class, () -> Address.fromHex(hexPublicKey.concat("1234")));
    }

    @Test
    void fromBech32() {
        assertDoesNotThrow(() -> Address.fromBech32(bech32));
        assertThrows(CannotDecodeBech32AddressException.class, () -> Address.fromBech32(bech32.concat("123")));
    }

    @Test
    void hex() {
        var address = Address.fromBech32(bech32);
        assertEquals(hexPublicKey, address.getHex());
    }

    @Test
    void publicKey() {
        var address = Address.fromBech32(bech32);
        assertArrayEquals(Hex.decode(hexPublicKey), address.getPublicKey());
    }

    @Test
    void bech32() {
        var address = Address.fromHex(hexPublicKey);
        assertEquals(bech32, address.getBech32());
    }

    @Test
    void zero() {
        assertEquals(AddressConstants.ZERO_PUBKEY_STRING, Address.zero().getHex());
    }
}