package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import lombok.Value;
import org.bouncycastle.util.encoders.Hex;

/**
 * Value object for transaction Hash
 *
 * @author carlo_stanciu
 */
@Value
public class Hash {

    byte[] value;

    private Hash(byte[] value) {
        this.value = value;
    }

    /**
     * Creates an Hash object from
     * a String value
     *
     * @param hash - value in String format
     * @return - an instance of Hash
     */
    public static Hash fromString(String hash) {
        return new Hash(Hex.decode(hash));
    }

    /**
     * Creates an Hash object from
     * a buffer
     *
     * @param buffer - value in buffer format
     * @return - an instance of Hash
     */
    public static Hash fromBuffer(byte[] buffer) {
        return new Hash(buffer);
    }

    /**
     * Creates an empty Hash
     *
     * @return - an instance of Hash
     */
    public static Hash empty() {
        return new Hash(new byte[]{});
    }

    /**
     * Checks if Hash is empty
     *
     * @return - boolean
     */
    public boolean isEmpty() {
        return value.length == 0;
    }

    /**
     * Getter
     *
     * @return -
     */
    public byte[] getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Hex.toHexString(value);
    }

}
