package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenPropertyName;

class TokenPropertyNameTest {

  @Test
  void testHexAndValue() {
    var pName = TokenPropertyName.CAN_ADD_SPECIAL_ROLES;

    assertEquals("63616e4164645370656369616c526f6c6573", pName.getHex());
    assertEquals("canAddSpecialRoles", pName.getValue());
  }

  @Test
  void testGetByValue() {
    assertEquals(TokenPropertyName.CAN_FREEZE, TokenPropertyName.getByValue("canFreeze"));
    assertThrows(IllegalArgumentException.class, () -> TokenPropertyName.getByValue("cannotFreeze"));
  }
}