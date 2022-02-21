package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import software.crldev.elrondspringbootstarterreactive.error.exception.CannotCreateSignatureException;
import software.crldev.elrondspringbootstarterreactive.error.exception.SignatureEmptyException;
import software.crldev.elrondspringbootstarterreactive.util.HexValidator;
import lombok.Value;
import org.bouncycastle.util.encoders.Hex;

import java.util.Arrays;

import static software.crldev.elrondspringbootstarterreactive.config.constants.TransactionConstants.SIGNATURE_LENGTH;
import static software.crldev.elrondspringbootstarterreactive.config.constants.TransactionConstants.SIGNATURE_LENGTH_HEX;

/**
 * Value object for Signature
 *
 * @author carlo_stanciu
 */
@Value
public class Signature {

    String hex;

    private Signature(String hex) {
        this.hex = hex;
    }

    /**
     * used for creating an Signature using
     * a hex representation of an Signable object
     *
     * @param hexValue - hex value of Signable object buffer
     * @return - an instance of Signature
     */
    public static Signature fromHex(String hexValue) {
        if (!HexValidator.isHexValid(hexValue, SIGNATURE_LENGTH_HEX))
            throw new CannotCreateSignatureException(hexValue);

        return new Signature(hexValue);
    }

    /**
     * used for creating an Signature using
     * a buffer representation of an Signable object
     *
     * @param buffer - buffer of Signable object buffer
     * @return - an instance of Signature
     */
    public static Signature fromBuffer(byte[] buffer) {
        if (buffer.length != SIGNATURE_LENGTH)
            throw new CannotCreateSignatureException(Arrays.toString(buffer));

        return new Signature(Hex.toHexString(buffer));
    }

    /**
     * Creates an empty Signature
     *
     * @return - an instance of Signature
     */
    public static Signature empty() {
        return new Signature("");
    }

    /**
     * Getter
     * Throws exception if empty
     *
     * @return - hex value of the Signature
     */
    public String getHex() {
        if (isEmpty())
            throw new SignatureEmptyException();

        return hex;
    }

    /**
     * Checks if Signature is empty
     *
     * @return - boolean
     */
    public boolean isEmpty() {
        return hex.isEmpty();
    }


}
