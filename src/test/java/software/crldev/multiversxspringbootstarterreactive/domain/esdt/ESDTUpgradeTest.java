package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedHashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenProperty;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenPropertyName;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;

class ESDTUpgradeTest {

  private static final Wallet wallet = Wallet.fromPrivateKeyHex(
      "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");

  @Test
  void upgrade() {
    var tokenIdentifier = TokenIdentifier.fromString("YMY-8888aa");

    var freezeProp = new TokenProperty(TokenPropertyName.CAN_FREEZE, true);
    var wipeProp = new TokenProperty(TokenPropertyName.CAN_WIPE, false);
    var pauseProp = new TokenProperty(TokenPropertyName.CAN_PAUSE, true);
    var changeProp = new TokenProperty(TokenPropertyName.CAN_CHANGE_OWNER, true);
    var upgradeProp = new TokenProperty(TokenPropertyName.CAN_UPGRADE, true);
    var specialRoleProp = new TokenProperty(TokenPropertyName.CAN_ADD_SPECIAL_ROLES, true);

    var props = new LinkedHashSet<>(List.of(freezeProp, wipeProp, pauseProp, changeProp,
        upgradeProp, specialRoleProp));

    var upgrade = ESDTUpgrade.builder()
        .tokenIdentifier(tokenIdentifier)
        .properties(props)
        .build();

    var req = upgrade.toTransactionRequest(wallet);

    assertEquals(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS).getBech32(),
        req.getReceiverAddress().getBech32());
    assertEquals(Balance.zero().getValue(), req.getValue().getValue());

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_UPGRADE_CALL, dataArgs[0]);
    assertEquals(tokenIdentifier.getHex(), dataArgs[1]);

    int index = 2;
    for (TokenProperty prop : props) {
      assertEquals(prop.getNameHex(), dataArgs[index++]);
      assertEquals(prop.getValueHex(), dataArgs[index++]);
    }
  }

}