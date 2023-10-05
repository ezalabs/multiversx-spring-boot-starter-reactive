package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Value;
import org.bouncycastle.util.encoders.Hex;

/**
 * Value object for ESDT token attributes
 *
 * @author carlo_stanciu
 */
@Value
public class TokenAttributes {

  ESDTUri metadataUri;
  Set<String> tags;

  private TokenAttributes(ESDTUri metadataUri, Set<String> tags) {
    this.metadataUri = metadataUri;
    this.tags = tags;
  }

  /**
   * Creates a TokenName object from String inputs
   *
   * @return - an instance of TokenAttributes
   */
  public static TokenAttributes fromString(String metadata, String[] tags) {
    verifyNullEmpty(tags);
    var tagsW = Arrays.stream(tags).map(TokenAttributes::removeWhitespace)
        .collect(Collectors.toSet());

    return new TokenAttributes(ESDTUri.fromString(metadata, ESDTUri.Type.METADATA), tagsW);
  }

  /**
   * Getter
   *
   * @return - hex value of the Token Attributes
   */
  public String getHex() {
    return Hex.toHexString(toString().getBytes());
  }

  /**
   * Returns the value
   *
   * @return - value in String format
   */
  @Override
  public String toString() {
    return "metadata:" + metadataUri.getValue() + ";tags:" + String.join(",", tags);
  }

  private static String removeWhitespace(String value) {
    return value.replaceAll("\\s+", "");
  }

  private static void verifyNullEmpty(String... value) {
    if (value == null || value.length == 0) {
      throw new IllegalArgumentException(ErrorMessage.TOKEN_ATTRIBUTES.getValue());
    }

    Arrays.stream(value).forEach(v -> {
      if (isNullOrEmpty(v)) {
        throw new IllegalArgumentException(ErrorMessage.TOKEN_ATTRIBUTES.getValue());
      }
    });
  }
}
