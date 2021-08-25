package software.crldev.elrondspringbootstarterreactive.util;

import org.bouncycastle.util.io.pem.PemReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;

/**
 * Utility class used for processing PEM files
 *
 * @author carlo_stanciu
 */
public class PemUtils {

    /**
     * Method used for extracting the private key in hex format
     * from a PEM file
     *
     * @param pem - target PEM file
     * @return - private key hex in String format
     * @throws IOException - if file invalid
     */
    public static String extractKeyHex(File pem) throws IOException {
        var pemObject = new PemReader(new FileReader(pem)).readPemObject();
        var keyBase64 = Base64.getEncoder().encodeToString(pemObject.getContent());
        var privateKeyBase64 = keyBase64.substring(0, keyBase64.length() / 2);

        return new String(Base64.getDecoder().decode(privateKeyBase64));
    }

}
