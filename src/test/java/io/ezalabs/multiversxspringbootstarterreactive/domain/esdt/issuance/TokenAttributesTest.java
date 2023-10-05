package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.issuance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenAttributes;

class TokenAttributesTest {

  @Test
  void fromString() {
    var tag1 = " first";
    var tag2 = " second ";
    var tags = new String[]{tag1, tag2};

    var attr = TokenAttributes.fromString("ipfs/CID/fileName.json", tags);

    assertEquals("metadata:ipfs/CID/fileName.json;tags:first,second", attr.toString());
    assertEquals("6d657461646174613a697066732f4349442f66696c654e616d652e6a736f6e3b746167733a66697273742c7365636f6e64",
        attr.getHex());
  }

  @Test
  void fromStringErr() {
    assertThrows(IllegalArgumentException.class, () -> TokenAttributes
        .fromString(null, null));
    assertThrows(IllegalArgumentException.class, () -> TokenAttributes
        .fromString("", new String[]{}));
    assertThrows(IllegalArgumentException.class, () -> TokenAttributes
        .fromString("ipfs/CID/fileName.json", new String[]{}));
    assertThrows(IllegalArgumentException.class, () -> TokenAttributes
        .fromString("ipfs/CID/fileName.txt", new String[]{"one"}));
  }

}