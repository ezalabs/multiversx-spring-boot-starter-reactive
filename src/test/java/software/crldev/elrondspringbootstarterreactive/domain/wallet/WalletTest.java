package software.crldev.elrondspringbootstarterreactive.domain.wallet;

import software.crldev.elrondspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.error.exception.PrivateKeyHexSizeException;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WalletTest {

    String publicKeyHex = "bf86ad970aa3c93c2382889c0a4fba4728b5b851634d57ccd65dcddeda8c8bb0";
    String privateKeyHex = "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62";
    byte[] privateKeyBuffer = Hex.decode(privateKeyHex);

    @Test
    void fromPrivateKeyHex() {
        var wallet = Wallet.fromPrivateKeyHex(privateKeyHex);

        assertArrayEquals(privateKeyBuffer, wallet.getPrivateKey());
        assertEquals(publicKeyHex, wallet.getPublicKeyHex());
    }

    @Test
    void fromPrivateKeyHex_sizeException() {
        assertThrows(PrivateKeyHexSizeException.class, () -> Wallet.fromPrivateKeyHex(privateKeyHex.concat("1")));
    }

    @Test
    void fromPrivateKeyBuffer() {
        var wallet = Wallet.fromPrivateKeyBuffer(privateKeyBuffer);

        assertEquals(privateKeyBuffer, wallet.getPrivateKey());
        assertEquals(publicKeyHex, wallet.getPublicKeyHex());
    }

    @Test
    void sign_success() {
        var signable = new Transaction();

        var wallet = Wallet.fromPrivateKeyHex(privateKeyHex);
        wallet.sign(signable);
    }

}