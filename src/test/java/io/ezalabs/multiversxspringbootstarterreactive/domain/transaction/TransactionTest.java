package io.ezalabs.multiversxspringbootstarterreactive.domain.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.ezalabs.multiversxspringbootstarterreactive.config.MxNetworkConfigSupplier;
import io.ezalabs.multiversxspringbootstarterreactive.config.constants.AddressConstants;
import io.ezalabs.multiversxspringbootstarterreactive.config.constants.TransactionConstants;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Nonce;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;


class TransactionTest {

  String senderAddress = "erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj";
  String receiverAddress = "erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj";
  String chainId = "D";
  Long nonce = 1L;
  String value = "5100000000000000000";
  String dataString = "crldev x elrond";
  String signature = "638b94dd2ee6c2e6abc51e393836bc849d8fd3db9cd4d86f1f62a359b8e61fc45dc712d74616e47088d0f1b538faba7aa44fa652be8b9150573c8c946ba1df06";

  @Test
  void verify_creation_signature_and_sendable() throws FileNotFoundException {
    var wallet = Wallet.fromPemFile(ResourceUtils.getFile("classpath:devnetAccount1.pem"));

    var transaction = new Transaction();
    verifyDefaults(transaction);

    transaction.setNonce(Nonce.fromLong(nonce));
    transaction.setChainID(ChainID.fromString(chainId));
    transaction.setValue(Balance.fromString(value));
    transaction.setSender(Address.fromBech32(senderAddress));
    transaction.setReceiver(Address.fromBech32(receiverAddress));
    transaction.setPayloadData(PayloadData.fromString(dataString));

    var sendable = transaction.toSendable();
    assertEquals(transaction.getGasLimit().getValue(), sendable.getGasLimit());
    assertEquals(transaction.getGasPrice().getValue(), sendable.getGasPrice());

    wallet.sign(transaction);

    System.out.println(transaction.getSignature().getHex());

    assertEquals(signature, transaction.getSignature().getHex());
  }

  @Test
  void verify_GasLimitAdjust_by_settingData() {
    var expectedCostWithoutData = BigInteger.valueOf(70000);
    var transaction = new Transaction();

    assertEquals(expectedCostWithoutData, transaction.getGasLimit().getValue());

    var expectedCostWithData = BigInteger.valueOf(92500);
    transaction.setPayloadData(PayloadData.fromString("crldev x elrond"));

    assertEquals(expectedCostWithData, transaction.getGasLimit().getValue());
  }


  private void verifyDefaults(Transaction transaction) {
    assertEquals(0L, (long) transaction.getNonce().getValue());
    assertEquals(MxNetworkConfigSupplier.config.getChainId(), transaction.getChainID().getValue());
    assertEquals(AddressConstants.ZERO_PUBKEY_STRING, transaction.getSender().getHex());
    assertEquals(AddressConstants.ZERO_PUBKEY_STRING, transaction.getReceiver().getHex());
    assertEquals(MxNetworkConfigSupplier.config.getMinGasPrice(), transaction.getGasPrice().getValue());
    assertEquals(MxNetworkConfigSupplier.config.getMinGasLimit(), transaction.getGasLimit().getValue());
    assertTrue(transaction.getPayloadData().isEmpty());
    assertEquals(TransactionConstants.TRANSACTION_VERSION_DEFAULT, transaction.getVersion().getValue());
    assertTrue(transaction.getSignature().isEmpty());
    assertTrue(transaction.getHash().isEmpty());
  }
}