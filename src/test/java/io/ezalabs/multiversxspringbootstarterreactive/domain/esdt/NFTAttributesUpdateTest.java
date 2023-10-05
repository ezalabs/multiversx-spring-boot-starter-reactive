package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import org.junit.jupiter.api.Test;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Nonce;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenAttributes;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;

class NFTAttributesUpdateTest {

  final Wallet wallet = Wallet.fromPrivateKeyHex("8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");

  @Test
  void create() {
    final var identifier = TokenIdentifier.fromString("YMY-19jf9a");
    final var nonce = Nonce.fromLong(2L);
    final var attributes = TokenAttributes.fromString("ipfsCID/fileName.json", new String[]{"one", "two"});

    var nft = NFTAttributesUpdate.builder()
        .tokenIdentifier(identifier)
        .nonce(nonce)
        .tokenAttributes(attributes)
        .build();

    var req = nft.toTransactionRequest(wallet);

    assertEquals(wallet.getAddress().getBech32(), req.getReceiverAddress().getBech32());
    assertEquals(Balance.zero().getValue(), req.getValue().getValue());
    assertEquals(GasLimit.defaultNftCreate().getValue(), req.getGasLimit().getValue());

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_NFT_UPDATE_ATTRIBUTES_CALL, dataArgs[0]);
    assertEquals(identifier.getHex(), dataArgs[1]);
    assertEquals(nonce.getHex(), dataArgs[2]);
    assertEquals(attributes.getHex(), dataArgs[3]);
  }

}