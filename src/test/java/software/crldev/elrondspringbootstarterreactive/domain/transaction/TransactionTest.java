package software.crldev.elrondspringbootstarterreactive.domain.transaction;

import software.crldev.elrondspringbootstarterreactive.config.AddressConstants;
import software.crldev.elrondspringbootstarterreactive.config.ErdNetworkConfigSupplier;
import software.crldev.elrondspringbootstarterreactive.config.TransactionConstants;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.ChainID;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.WalletCreator;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;


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
        var wallet = WalletCreator.fromPemFile(ResourceUtils.getFile("classpath:devnetAccount1.pem"));

        var transaction = new Transaction();
        verifyDefaults(transaction);

        transaction.setNonce(Nonce.fromLong(nonce));
        transaction.setChainID(ChainID.fromString(chainId));
        transaction.setValue(Balance.fromString(value));
        transaction.setSender(Address.fromBech32(senderAddress));
        transaction.setReceiver(Address.fromBech32(receiverAddress));
        transaction.setPayloadData(PayloadData.fromString(dataString));

//        var estimationSendable = transaction.toSendableForEstimation();
//        assertEquals(1L, estimationSendable.getNonce());
//        assertEquals(value, estimationSendable.getValue());
//        assertEquals(receiverAddress, estimationSendable.getReceiver());
//        assertEquals(senderAddress, estimationSendable.getSender());
//        assertNull(estimationSendable.getGasLimit());
//        assertNull(estimationSendable.getGasPrice());

        var regularSendable = transaction.toSendable();
        assertEquals(transaction.getGasLimit().getValue(), regularSendable.getGasLimit());
        assertEquals(transaction.getGasPrice().getValue(), regularSendable.getGasPrice());

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
        assertEquals(ErdNetworkConfigSupplier.config.getChainId(), transaction.getChainID().getValue());
        assertEquals(AddressConstants.ZERO_PUBKEY_STRING, transaction.getSender().getHex());
        assertEquals(AddressConstants.ZERO_PUBKEY_STRING, transaction.getReceiver().getHex());
        assertEquals(ErdNetworkConfigSupplier.config.getMinGasPrice(), transaction.getGasPrice().getValue());
        assertEquals(ErdNetworkConfigSupplier.config.getMinGasLimit(), transaction.getGasLimit().getValue());
        assertTrue(transaction.getPayloadData().isEmpty());
        assertEquals(TransactionConstants.TRANSACTION_VERSION_DEFAULT, transaction.getVersion().getValue());
        assertTrue(transaction.getSignature().isEmpty());
        assertTrue(transaction.getHash().isEmpty());
    }
}