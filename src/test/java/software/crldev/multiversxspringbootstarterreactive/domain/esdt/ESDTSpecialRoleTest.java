package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_BURN_ROLE;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_MINT_ROLE;
import static software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole.ESDT_LOCAL_BURN;
import static software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole.ESDT_LOCAL_MINT;

import org.bouncycastle.util.encoders.Hex;
import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;

class ESDTSpecialRoleTest {

  @Test
  void testHexAndValue() {
    var pName = ESDT_LOCAL_MINT;

    assertEquals(Hex.toHexString(ESDT_MINT_ROLE.getBytes()), pName.getHex());
    assertEquals(ESDT_MINT_ROLE, pName.getValue());
  }

  @Test
  void testGetByValue() {
    assertEquals(ESDT_LOCAL_BURN, ESDTSpecialRole.getByValue(ESDT_BURN_ROLE));
    assertThrows(IllegalArgumentException.class, () -> ESDTSpecialRole.getByValue("mintRole"));
  }

}