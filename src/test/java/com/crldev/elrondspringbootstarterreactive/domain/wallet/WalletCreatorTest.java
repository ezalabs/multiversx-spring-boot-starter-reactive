package com.crldev.elrondspringbootstarterreactive.domain.wallet;

import software.crldev.elrondspringbootstarterreactive.domain.wallet.WalletCreator;
import software.crldev.elrondspringbootstarterreactive.error.exception.CannotCreateWalletException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class WalletCreatorTest {

    String publicKeyHex = "bf86ad970aa3c93c2382889c0a4fba4728b5b851634d57ccd65dcddeda8c8bb0";
    String privateKeyHex = "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62";
    byte[] privateKeyBuffer = Hex.decode(privateKeyHex);

    @Test
    void testPem_success() throws Exception {
        var pemFile = ResourceUtils.getFile("classpath:devnetAccount1.pem");
        var wallet = WalletCreator.fromPemFile(pemFile);

        assertEquals(publicKeyHex, wallet.getPublicKeyHex());
        assertEquals(privateKeyHex, Hex.toHexString(wallet.getPrivateKey()));
    }

    @Test
    void testPem_fail() throws Exception {
        var pemFile = File.createTempFile("error", ".pem");

        assertThrows(CannotCreateWalletException.class,
                () -> WalletCreator.fromPemFile(pemFile));
    }

}