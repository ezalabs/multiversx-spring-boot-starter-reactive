package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import lombok.Value;
import org.bouncycastle.util.encoders.Base64;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;

/**
 * Value object for transaction PayloadData
 *
 * @author carlo_stanciu
 */
@Value
public class PayloadData {

    byte[] buffer;

    private PayloadData(byte[] buffer) {
        this.buffer = buffer;
    }

    /**
     * Creates an PayloadData object from
     * a data String
     *
     * @param value - data String value
     * @return - an instance of PayloadData
     */
    public static PayloadData fromString(String value) {
        return new PayloadData(value.getBytes());
    }

    /**
     * Creates an PayloadData object from
     * a data encoded String
     *
     * @param encoded - data Base64 encoded String value
     * @return - an instance of PayloadData
     */
    public static PayloadData fromEncoded(String encoded) {
        return isNullOrEmpty(encoded) ? PayloadData.empty() : new PayloadData(Base64.decode(encoded));
    }


    /**
     * Creates an empty PayloadData
     *
     * @return - an instance of PayloadData
     */
    public static PayloadData empty() {
        return new PayloadData(new byte[]{});
    }

    /**
     * Checks if PayloadData is empty
     *
     * @return - boolean
     */
    public boolean isEmpty() {
        return length() == 0;
    }

    /**
     * Retrieves the length of the data
     *
     * @return - length of data buffer
     */
    public int length() {
        return buffer.length;
    }

    /**
     * Retrieves the Base64 encoded data
     *
     * @return - data String Base64 encoded
     */
    public String encoded() {
        return new String(Base64.encode(buffer));
    }

    @Override
    public String toString() {
        return new String(buffer);
    }

}
