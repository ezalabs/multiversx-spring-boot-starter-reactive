package software.crldev.multiversxspringbootstarterreactive.domain.esdt.role;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.NFTCreationRoleTransfer;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;

class NFTCreationRoleTransferTest {

  private static final Wallet wallet = Wallet.fromPrivateKeyHex(
      "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");
  private static final Address toAddress = Address.fromBech32(
      "erd1gklqdv77my5y8n75hszv737gq54q9xk0tmzdh8v5vkfstd64aw7ser9nfr");

  @Test
  void transferRole() {
    final var identifier = TokenIdentifier.fromString("YMY-8888aa");

    var transfer = NFTCreationRoleTransfer.builder()
        .tokenIdentifier(identifier)
        .fromAddress(wallet.getAddress())
        .toAddress(toAddress)
        .build();

    var req = transfer.toTransactionRequest(wallet);

    assertEquals(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS).getBech32(),
        req.getReceiverAddress().getBech32());
    assertEquals(GasLimit.defaultEsdtIssuance().getValue(), req.getGasLimit().getValue());
    assertTrue(req.getValue().isZero());

    var dataArgs = req.getData().toString().split("@");
    assertEquals(ESDTConstants.ESDT_NFT_CREATE_ROLE_TRANSFER_CALL, dataArgs[0]);
    assertEquals(identifier.getHex(), dataArgs[1]);
    assertEquals(wallet.getAddress().getHex(), dataArgs[2]);
    assertEquals(toAddress.getHex(), dataArgs[3]);
  }

}