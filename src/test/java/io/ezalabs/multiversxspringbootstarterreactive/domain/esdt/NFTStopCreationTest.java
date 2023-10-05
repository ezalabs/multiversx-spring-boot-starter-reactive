package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;

class NFTStopCreationTest {

  private static final Wallet wallet = Wallet.fromPrivateKeyHex(
      "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");

  @Test
  void transferRole() {
    final var identifier = TokenIdentifier.fromString("YMY-8888aa");

    var stopCreation = NFTStopCreation.builder()
        .tokenIdentifier(identifier)
        .build();

    var req = stopCreation.toTransactionRequest(wallet);

    assertEquals(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS).getBech32(),
        req.getReceiverAddress().getBech32());
    assertEquals(GasLimit.defaultEsdtIssuance().getValue(), req.getGasLimit().getValue());
    assertTrue(req.getValue().isZero());

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_NFT_STOP_CREATION_CALL, dataArgs[0]);
    assertEquals(identifier.getHex(), dataArgs[1]);
  }

}