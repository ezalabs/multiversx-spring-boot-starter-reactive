package software.crldev.elrondspringbootstarterreactive.util;

import org.bouncycastle.util.encoders.Hex;

/**
 * Utility class for HEX validation
 *
 * @author carlo_stanciu
 */
public class HexValidator {

    /**
     * Method used for verifying if a HEX String is valid
     *
     * @param hexValue       - HEX value in String format
     * @param requiredLength - length condition
     * @return - true if valid, false if not
     */
    public static boolean isHexValid(String hexValue, Integer requiredLength) {
        try {
            Hex.decode(hexValue);
            return hexValue.length() == requiredLength;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Method used for processing hex number arguments for transactions
     *
     * @param hex - HEX value in String format
     * @return - HEX value with even nr of chars
     */
    public static String processNumberHexArgument(String hex) {
        if (!(hex.length() % 2 == 0))
            return "0" + hex;

        return hex;
    }
}
