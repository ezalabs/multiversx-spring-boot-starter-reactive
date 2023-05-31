package software.crldev.multiversxspringbootstarterreactive.domain.esdt.issuance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTUri;

class ESDTUriTest {

  @Test
  void invalidURI() {
    assertThrows(IllegalArgumentException.class, () -> ESDTUri.fromString("f3/f -9fa92", ESDTUri.Type.MEDIA));
  }

  @Test
  void fromStringMeta() {
    var uri = ESDTUri.fromString("https://host/storage/metadata.json", ESDTUri.Type.METADATA);
    assertEquals("68747470733a2f2f686f73742f73746f726167652f6d657461646174612e6a736f6e",
        uri.getHex());
  }

  @Test
  void fromStringMedia() {
    var uri = ESDTUri.fromString("https://host/storage/image.jpeg", ESDTUri.Type.MEDIA);
    assertEquals("68747470733a2f2f686f73742f73746f726167652f696d6167652e6a706567",
        uri.getHex());
  }

  @Test
  void fromStringMeta_notJson() {
    assertThrows(IllegalArgumentException.class,
        () -> ESDTUri.fromString("https://host/storage/metadata.notJson", ESDTUri.Type.METADATA));
  }

  @Test
  void fromStringMedia_notSupported() {
    assertThrows(IllegalArgumentException.class,
        () -> ESDTUri.fromString("https://host/storage/image.another", ESDTUri.Type.MEDIA));
  }
}