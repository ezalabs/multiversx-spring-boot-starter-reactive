package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenDecimals;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenInitialSupply;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenName;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenProperty;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenPropertyName;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenTicker;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;

class ESDTIssuanceTest {

  final Wallet wallet = Wallet.fromPrivateKeyHex("8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");
  final TokenName tokenName = TokenName.fromString("Yummy Token");
  final TokenTicker tokenTicker = TokenTicker.fromString("YMY");
  final TokenProperty freezeProp = new TokenProperty(TokenPropertyName.CAN_FREEZE, true);
  final TokenProperty wipeProp = new TokenProperty(TokenPropertyName.CAN_WIPE, false);
  final TokenProperty pauseProp = new TokenProperty(TokenPropertyName.CAN_PAUSE, true);
  final TokenProperty changeProp = new TokenProperty(TokenPropertyName.CAN_CHANGE_OWNER, true);
  final TokenProperty upgradeProp = new TokenProperty(TokenPropertyName.CAN_UPGRADE, true);
  final TokenProperty specialRoleProp = new TokenProperty(TokenPropertyName.CAN_ADD_SPECIAL_ROLES, true);
  final TokenProperty canTransferNftProp = new TokenProperty(TokenPropertyName.CAN_TRANSFER_NFT_CREATE_ROLE, true);

  final LinkedHashSet<TokenProperty> props = new LinkedHashSet<>(List.of(freezeProp, wipeProp, pauseProp,
      changeProp, upgradeProp, specialRoleProp, canTransferNftProp));

  final TokenInitialSupply supply = TokenInitialSupply.fromNumber(BigInteger.valueOf(10_000L));
  final TokenDecimals decimals = TokenDecimals.fromNumber(0);

  @Test
  void issueFungible() {
    var fungibleIssuance = builderWithAllProps()
        .type(ESDTIssuance.Type.FUNGIBLE)
        .initialSupply(supply)
        .decimals(decimals)
        .build();

    var req = fungibleIssuance.toTransactionRequest(wallet);

    assertEquals(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS).getBech32(),
        req.getReceiverAddress().getBech32());
    assertEquals(Balance.fromNumber(ESDTConstants.ESDT_ISSUANCE_EGLD_COST).getValue(), req.getValue().getValue());

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_FUNGIBLE_ISSUANCE_CALL, dataArgs[0]);
    assertEquals(tokenName.getHex(), dataArgs[1]);
    assertEquals(tokenTicker.getHex(), dataArgs[2]);
    assertEquals(supply.getHex(), dataArgs[3]);
    assertEquals(decimals.getHex(), dataArgs[4]);

    var index = 5;
    for (TokenProperty prop : props) {
      assertEquals(prop.getNameHex(), dataArgs[index++]);
      assertEquals(prop.getValueHex(), dataArgs[index++]);
    }

    assertFalse(Arrays.asList(dataArgs).subList(5, dataArgs.length)
        .contains(canTransferNftProp.getNameHex()));
  }

  @Test
  void issueSemiFungible() {
    var sFungibleIssuance = builderWithAllProps()
        .type(ESDTIssuance.Type.SEMI_FUNGIBLE)
        .build();

    var req = sFungibleIssuance.toTransactionRequest(wallet);

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_SEMI_FUNGIBLE_ISSUE_CALL, dataArgs[0]);

    checkUnwantedProps(dataArgs);
  }

  @Test
  void issueNonFungible() {
    var nFungibleIssuance = builderWithAllProps()
        .type(ESDTIssuance.Type.NON_FUNGIBLE)
        .build();

    var req = nFungibleIssuance.toTransactionRequest(wallet);

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_NON_FUNGIBLE_ISSUE_CALL, dataArgs[0]);

    checkUnwantedProps(dataArgs);
  }

  @Test
  void issueMeta() {
    var mIssuance = builderWithAllProps()
        .type(ESDTIssuance.Type.META)
        .decimals(decimals)
        .build();

    var req = mIssuance.toTransactionRequest(wallet);

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.META_ESDT_ISSUE_CALL, dataArgs[0]);

    checkUnwantedProps(dataArgs);
  }

  @Test
  void errorMissingValues() {
    var fungWithoutSupply = builderWithAllProps()
        .type(ESDTIssuance.Type.FUNGIBLE)
        .decimals(decimals)
        .build();

    assertEquals(ErrorMessage.INITIAL_SUPPLY.getValue(), assertThrows(IllegalArgumentException.class,
        () -> fungWithoutSupply.toTransactionRequest(wallet)).getMessage());

    var fungWithoutDecimals = builderWithAllProps()
        .type(ESDTIssuance.Type.FUNGIBLE)
        .initialSupply(supply)
        .build();
    assertEquals(ErrorMessage.TOKEN_DECIMALS.getValue(), assertThrows(IllegalArgumentException.class,
        () -> fungWithoutDecimals.toTransactionRequest(wallet)).getMessage());

    var metaWithoutDecimals = builderWithAllProps()
        .type(ESDTIssuance.Type.META)
        .build();
    assertEquals(ErrorMessage.TOKEN_DECIMALS.getValue(), assertThrows(IllegalArgumentException.class,
        () -> metaWithoutDecimals.toTransactionRequest(wallet)).getMessage());
  }

  private ESDTIssuance.ESDTIssuanceBuilder builderWithAllProps() {
    return ESDTIssuance.builder()
        .tokenName(tokenName)
        .tokenTicker(tokenTicker)
        .properties(props);
  }

  private void checkUnwantedProps(String[] dataArgs) {
    var propsList = Arrays.asList(dataArgs).subList(5, dataArgs.length);

    assertFalse(propsList.contains(changeProp.getNameHex()));
    assertFalse(propsList.contains(upgradeProp.getNameHex()));
    assertFalse(propsList.contains(specialRoleProp.getNameHex()));
  }

}