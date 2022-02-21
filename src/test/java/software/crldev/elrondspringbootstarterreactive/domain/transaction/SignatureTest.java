package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.error.exception.CannotCreateSignatureException;
import software.crldev.elrondspringbootstarterreactive.error.exception.SignatureEmptyException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SignatureTest {

    String signatureHex = "0b80e9e08732fa0dd84bf3f39d84447cbf1ac84f1417938c73ef0c1ea652a19eef13479143d19260324965676941fc7126a9e4638db766110a4f3d3d57422309";
    String invalidSignatureHex = "696e76616c69642d7369676e6174757265";

    @Test
    void fromHex() {
        assertEquals(signatureHex, Signature.fromHex(signatureHex).getHex());
        assertThrows(CannotCreateSignatureException.class, () -> Signature.fromHex(invalidSignatureHex));
    }

    @Test
    void fromBuffer() {
        var signatureBuffer = Hex.decode(signatureHex);
        var invalidSignatureBuffer = Hex.decode(invalidSignatureHex);

        assertEquals(signatureHex, Signature.fromBuffer(signatureBuffer).getHex());
        assertThrows(CannotCreateSignatureException.class, () -> Signature.fromBuffer(invalidSignatureBuffer));
    }

    @Test
    void empty() {
        assertThrows(SignatureEmptyException.class, () -> Signature.empty().getHex());
    }

}