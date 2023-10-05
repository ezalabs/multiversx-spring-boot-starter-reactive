package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole.ESDT_LOCAL_BURN;
import static io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole.ESDT_LOCAL_MINT;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;

class ESDTSpecialRoleTest {

  @Test
  void testHexAndValue() {
    var pName = ESDT_LOCAL_MINT;

    assertEquals(Hex.toHexString(ESDTConstants.ESDT_MINT_ROLE.getBytes()), pName.getHex());
    assertEquals(ESDTConstants.ESDT_MINT_ROLE, pName.getValue());
  }

  @Test
  void testGetByValue() {
    assertEquals(ESDT_LOCAL_BURN, ESDTSpecialRole.getByValue(ESDTConstants.ESDT_BURN_ROLE));
    assertThrows(IllegalArgumentException.class, () -> ESDTSpecialRole.getByValue("mintRole"));
  }

}