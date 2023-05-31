package software.crldev.multiversxspringbootstarterreactive.domain.esdt.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_MULTI_TRANSFER_GAS_COST_BASE;
import static software.crldev.multiversxspringbootstarterreactive.util.HexValidator.processNumberHexArgument;

import java.math.BigInteger;
import java.util.List;
import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTNFTMultiTransfer;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TransferToken;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;

class ESDTNFTMultiTransferTest {

  private static final Wallet wallet = Wallet.fromPrivateKeyHex(
      "8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");

  @Test
  void transfer() {
    var tokenList = List.of(
        TransferToken.builder()
            .identifier(TokenIdentifier.fromString("TKN-3a249e"))
            .nonce(Nonce.fromLong(2L))
            .amount(Balance.fromNumber(BigInteger.TEN))
            .build(),
        TransferToken.builder()
            .identifier(TokenIdentifier.fromString("PLM-4a239F"))
            .amount(Balance.fromNumber(BigInteger.TWO))
            .build(),
        TransferToken.builder()
            .identifier(TokenIdentifier.fromString("RPD-1E468L"))
            .nonce(Nonce.fromLong(3L))
            .amount(Balance.fromNumber(BigInteger.ONE))
            .build());

    var receiverAddress = Address.fromBech32("erd1gklqdv77my5y8n75hszv737gq54q9xk0tmzdh8v5vkfstd64aw7ser9nfr");

    var transfer = ESDTNFTMultiTransfer.builder()
        .receiverAddress(receiverAddress)
        .tokenList(tokenList)
        .build();

    var request = transfer.toTransactionRequest(wallet);

    assertEquals(wallet.getAddress().getBech32(), request.getReceiverAddress().getBech32());
    assertEquals(GasLimit.fromNumber(ESDT_MULTI_TRANSFER_GAS_COST_BASE
        .multiply(BigInteger.valueOf(tokenList.size()))), request.getGasLimit());
    assertEquals(Balance.zero(), request.getValue());

    var dataArgs = request.getData().toString().split("@");
    assertEquals("MultiESDTNFTTransfer", dataArgs[0]);
    assertEquals(receiverAddress.getHex(), dataArgs[1]);
    assertEquals(processNumberHexArgument(Integer.toString(tokenList.size(), 16)),
        dataArgs[2]);

    for (int i = 0, j = 3; i < tokenList.size(); i++) {
      assertEquals(tokenList.get(i).getIdentifier().getHex(), dataArgs[j++ + i]);
      assertEquals(tokenList.get(i).getNonce().getHex(), dataArgs[j++ + i]);
      assertEquals(tokenList.get(i).getAmount().getHex(), dataArgs[j + i]);
    }
  }

}