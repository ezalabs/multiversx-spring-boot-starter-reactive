package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole.ESDT_LOCAL_BURN;
import static software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole.ESDT_LOCAL_MINT;

import java.util.LinkedHashSet;
import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;

class ESDTRoleAssignmentTest {

  private static final Wallet wallet = Wallet.fromPrivateKeyHex(
      "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");
  private final TokenIdentifier identifier = TokenIdentifier.fromString("TKN-98ah2h");

  @Test
  void testSet() {
    var roles = new LinkedHashSet<ESDTSpecialRole>();
    roles.add(ESDT_LOCAL_BURN);
    roles.add(ESDT_LOCAL_MINT);

    var assignment = builder().type(ESDTRoleAssignment.Type.SET)
        .roles(roles)
        .build();
    var req = assignment.toTransactionRequest(wallet);

    assertEquals(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS).getBech32(),
        req.getReceiverAddress().getBech32());
    assertEquals(GasLimit.defaultEsdtIssuance().getValue(), req.getGasLimit().getValue());
    assertEquals(Balance.zero().getValue(), req.getValue().getValue());

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_SET_ROLE_CALL, dataArgs[0]);
    assertEquals(identifier.getHex(), dataArgs[1]);
    assertEquals(wallet.getAddress().getHex(), dataArgs[2]);
    assertEquals(ESDT_LOCAL_BURN.getHex(), dataArgs[3]);
    assertEquals(ESDT_LOCAL_MINT.getHex(), dataArgs[4]);
  }

  @Test
  void testUnset() {
    var roles = new LinkedHashSet<ESDTSpecialRole>();
    roles.add(ESDT_LOCAL_BURN);

    var assignment = builder().type(ESDTRoleAssignment.Type.UNSET)
        .roles(roles)
        .build();
    var req = assignment.toTransactionRequest(wallet);

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_UNSET_ROLE_CALL, dataArgs[0]);
    assertEquals(identifier.getHex(), dataArgs[1]);
    assertEquals(wallet.getAddress().getHex(), dataArgs[2]);
    assertEquals(ESDT_LOCAL_BURN.getHex(), dataArgs[3]);
  }

  @Test
  void testEmptyRoles() {
    var assignment = builder().type(ESDTRoleAssignment.Type.UNSET)
        .roles(new LinkedHashSet<>())
        .build();
    assertThrows(IllegalArgumentException.class, () -> assignment.toTransactionRequest(wallet));
  }

  private ESDTRoleAssignment.ESDTRoleAssignmentBuilder builder() {
    return ESDTRoleAssignment.builder()
        .address(wallet.getAddress())
        .tokenIdentifier(identifier);
  }

}