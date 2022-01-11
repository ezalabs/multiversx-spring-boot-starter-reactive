package software.crldev.elrondspringbootstarterreactive.util;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.error.exception.InvalidPemFileException;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
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
     * @param pem - target PEM file (in File format)
     * @return - private key hex in String format
     */
    public static String extractKeyHex(File pem) {
        try {
            return processPem(new FileReader(pem));
        } catch (Exception e) {
            throw new InvalidPemFileException(e);
        }
    }

    /**
     * Method used for extracting the private key in hex format
     * from a PEM file
     * Used in reactive steam input
     *
     * @param pem - target PEM file (in FilePart format)
     * @return - private key hex in String format
     */
    public static Mono<String> extractKeyHex(FilePart pem) {
        return pem.content()
                .map(DataBuffer::asInputStream)
                .next()
                .map(is -> processPem(new InputStreamReader(is)));
    }

    private static String processPem(Reader reader) {
        PemObject pemObject;
        try {
            pemObject = new PemReader(reader).readPemObject();
            var keyBase64 = Base64.getEncoder().encodeToString(pemObject.getContent());
            var privateKeyBase64 = keyBase64.substring(0, keyBase64.length() / 2);

            return new String(Base64.getDecoder().decode(privateKeyBase64));
        } catch (Exception e) {
            throw new InvalidPemFileException(e);
        }
    }


}
