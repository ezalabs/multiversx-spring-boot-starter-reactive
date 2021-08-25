package software.crldev.elrondspringbootstarterreactive.domain.wallet;

import software.crldev.elrondspringbootstarterreactive.config.WalletConstants;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Signable;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Signature;
import software.crldev.elrondspringbootstarterreactive.error.exception.CannotCreateSignatureException;
import software.crldev.elrondspringbootstarterreactive.error.exception.CannotSignTransactionException;
import software.crldev.elrondspringbootstarterreactive.error.exception.PrivateKeyHexSizeException;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Value;
import org.bouncycastle.crypto.params.Ed25519PrivateKeyParameters;
import org.bouncycastle.crypto.signers.Ed25519Signer;
import org.bouncycastle.util.encoders.Hex;

/**
 * Value object for Wallet
 */
@Value
public class Wallet {

    byte[] publicKey;
    byte[] privateKey;

    private Wallet(byte[] publicKey, byte[] privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    /**
     * Method used for creating a Wallet using a private key buffer
     *
     * @param privateKey - private key in buffer format
     * @return - an instance of Wallet
     */
    public static Wallet fromPrivateKeyBuffer(byte[] privateKey) {
        var privateKeyParameters = new Ed25519PrivateKeyParameters(privateKey, 0);
        var publicKeyParameters = privateKeyParameters.generatePublicKey();

        return new Wallet(publicKeyParameters.getEncoded(), privateKey);
    }

    /**
     * Method used for creating a Wallet using a private key hex
     *
     * @param privateKeyHex - private key in hex representation
     * @return - an instance of Wallet
     */
    public static Wallet fromPrivateKeyHex(String privateKeyHex) {
        if (privateKeyHex.length() == WalletConstants.PRIVATE_KEY_LENGTH)
            return fromPrivateKeyBuffer(Hex.decode(privateKeyHex));

        throw new PrivateKeyHexSizeException(privateKeyHex.length());
    }

    /**
     * Method used for signing a Signable object
     * using Ed25519Signer
     *
     * @param signable - object to sign
     */
    public void sign(Signable signable) {
        try {
            var data = signable.serializeForSigning();
            var signer = createEd25519Signer();
            signer.update(data, 0, data.length);
            signable.applySignature(Signature.fromBuffer(signer.generateSignature()));
        } catch (JsonProcessingException | CannotCreateSignatureException e) {
            throw new CannotSignTransactionException(e);
        }
    }

    /**
     * Getter
     *
     * @return - hex value of public key
     */
    public String getPublicKeyHex() {
        return Hex.toHexString(publicKey);
    }

    private Ed25519Signer createEd25519Signer() {
        var parameters = new Ed25519PrivateKeyParameters(this.privateKey, 0);
        var signer = new Ed25519Signer();
        signer.init(true, parameters);

        return signer;
    }

}
