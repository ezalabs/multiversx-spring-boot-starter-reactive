package software.crldev.elrondspringbootstarterreactive.domain.esdt.common;

import lombok.Builder;
import lombok.Value;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.web.util.UriUtils;
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

import java.net.URI;
import java.net.URISyntaxException;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;

/**
 * Value object for ESDT URI
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTUri {

    String value;

    private ESDTUri(String value) {
        this.value = value;
    }

    /**
     * Creates a ESDTUri object from String and Type inputs
     *
     * @return - an instance of ESDTUri
     */
    public static ESDTUri fromString(String value, Type type) {
        verifyNullEmpty(value, type);
        var valueW = removeWhitespace(value);

        try {
            new URI(value);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URI syntax.");
        }

        verifyExtension(UriUtils.extractFileExtension(valueW), type);
        return new ESDTUri(valueW);
    }

    /**
     * Getter
     *
     * @return - hex value of the URI
     */
    public String getHex() {
        return Hex.toHexString(value.getBytes());
    }

    private static void verifyExtension(String extension, Type type) {
        verifyNullEmpty(extension, type);

        if (type.equals(Type.METADATA)) {
            if (!extension.equalsIgnoreCase("json"))
                throw new IllegalArgumentException(ErrorMessage.NFT_METADATA_URI.getValue());
        } else {
            switch (extension.toLowerCase()) {
                case "png":
                case "jpeg":
                case "jpg":
                case "gif":
                case "acc":
                case "flac":
                case "m4a":
                case "mp3":
                case "wav":
                case "mov":
                case "quicktime":
                case "mp4":
                case "webm":
                    break;
                default:
                    throw new IllegalArgumentException(ErrorMessage.NFT_MEDIA_URI.getValue());
            }
        }
    }

    private static String removeWhitespace(String value) {
        return value.replaceAll("\\s+", "");
    }

    private static void verifyNullEmpty(String value, Type type) {
        if (isNullOrEmpty(value)) {
            if (type.equals(Type.METADATA))
                throw new IllegalArgumentException(ErrorMessage.NFT_METADATA_URI.getValue());
            throw new IllegalArgumentException(ErrorMessage.NFT_MEDIA_URI.getValue());
        }
    }

    public enum Type {METADATA, MEDIA}

}
