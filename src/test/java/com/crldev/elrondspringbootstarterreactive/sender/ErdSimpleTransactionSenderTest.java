package com.crldev.elrondspringbootstarterreactive.sender;

import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.error.exception.MissingTransactionRequestException;
import software.crldev.elrondspringbootstarterreactive.interactor.account.ErdAccountInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.SendableTransaction;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.crldev.elrondspringbootstarterreactive.sender.ErdSimpleTransactionSender;
import software.crldev.elrondspringbootstarterreactive.sender.ErdSimpleTransactionSenderImpl;
import software.crldev.elrondspringbootstarterreactive.sender.TransactionRequest;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ErdSimpleTransactionSenderTest {

    ErdSimpleTransactionSender sender;

    @Mock
    ErdAccountInteractor accountInteractor;
    @Mock
    ErdTransactionInteractor transactionInteractor;

    @Captor
    ArgumentCaptor<SendableTransaction> sendableCaptor;
    @Captor
    ArgumentCaptor<List<SendableTransaction>> sendablesCaptor;

    String senderAddressBech32 = "erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj";
    String receiverAddressBech32 = "erd1gklqdv77my5y8n75hszv737gq54q9xk0tmzdh8v5vkfstd64aw7ser9nfr";
    Wallet wallet = Wallet.fromPrivateKeyHex("8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");
    Long nonceValue = 10L;
    String dataValueText = "crldev x elrond";
    String dataValueBase64 = Base64.getEncoder().encodeToString(dataValueText.getBytes());

    @BeforeEach
    void setUp() {
        sender = new ErdSimpleTransactionSenderImpl(accountInteractor, transactionInteractor);
    }

    @Test
    void send() {
        mockNonce();

        var request = buildRequest(1.00, dataValueText);

        when(transactionInteractor.sendTransaction(sendableCaptor.capture()))
                .thenReturn(Mono.just(TransactionHash.builder().hash("1234").build()));

        StepVerifier.create(sender.send(request))
                .assertNext(r -> {
                    var sendable = sendableCaptor.getValue();

                    assertEquals("1000000000000000000", sendable.getValue());
                    assertEquals(nonceValue, sendable.getNonce());
                    assertEquals(senderAddressBech32, sendable.getSender());
                    assertEquals(receiverAddressBech32, sendable.getReceiver());
                    assertNotNull(sendable.getGasLimit());
                    assertEquals(dataValueBase64, sendable.getData());

                    assertEquals(r.getHash(), "1234");
                })
                .verifyComplete();
    }

    @Test
    void sendMultiple() {
        mockNonce();

        var dataValueText2 = "hello world";
        var dataValueBase64_2 = Base64.getEncoder().encodeToString(dataValueText2.getBytes());

        var request1 = buildRequest(1.00, dataValueText);
        var request2 = buildRequest(2.00, dataValueText2);

        var listOfRequests = List.of(request1, request2);

        when(transactionInteractor.sendBatchOfTransactions(sendablesCaptor.capture()))
                .thenReturn(Mono.just(TransactionsSentResult.builder()
                        .numberOfSentTransactions(2)
                        .transactionsHashes(new HashMap<>() {{
                            put("0", "2352523");
                            put("1", "43093403");
                        }})
                        .build()));

        StepVerifier.create(sender.sendBatchOfTransactions(listOfRequests))
                .assertNext(r -> {
                    var listOfSendables = sendablesCaptor.getValue();

                    var sendable1 = listOfSendables.get(0);
                    var sendable2 = listOfSendables.get(1);

                    assertEquals("1000000000000000000", sendable1.getValue());
                    assertEquals("2000000000000000000", sendable2.getValue());

                    assertEquals(nonceValue, sendable1.getNonce());
                    assertEquals(nonceValue + 1, sendable2.getNonce());
                    assertEquals(senderAddressBech32, sendable1.getSender());
                    assertEquals(senderAddressBech32, sendable2.getSender());
                    assertEquals(receiverAddressBech32, sendable1.getReceiver());
                    assertEquals(receiverAddressBech32, sendable2.getReceiver());

                    assertEquals(dataValueBase64, sendable1.getData());
                    assertEquals(dataValueBase64_2, sendable2.getData());

                    assertEquals(2, r.getNumberOfSentTransactions());
                    assertEquals("2352523", r.getTransactionsHashes().get("0"));
                    assertEquals("43093403", r.getTransactionsHashes().get("1"));

                })
                .verifyComplete();
    }

    @Test
    void sendMultiple_emptyList() {
        assertThrows(MissingTransactionRequestException.class, () -> sender.sendBatchOfTransactions(Lists.emptyList()));
    }

    @Test
    void simulate() {
        mockNonce();

        var request = buildRequest(1.00, dataValueText);

        when(transactionInteractor.simulateTransaction(sendableCaptor.capture()))
                .thenReturn(Mono.just(SimulationResults.builder()
                        .senderShard(ShardFromSimulatedTransaction.builder()
                                .status("success")
                                .build())
                        .receiverShard(ShardFromSimulatedTransaction.builder()
                                .status("success")
                                .build())
                        .build()));

        StepVerifier.create(sender.simulate(request))
                .assertNext(r -> {
                    var sendable = sendableCaptor.getValue();

                    assertEquals("1000000000000000000", sendable.getValue());
                    assertEquals(nonceValue, sendable.getNonce());
                    assertEquals(senderAddressBech32, sendable.getSender());
                    assertEquals(receiverAddressBech32, sendable.getReceiver());
                    assertNotNull(sendable.getGasLimit());
                    assertEquals(dataValueBase64, sendable.getData());

                    assertEquals("success", r.getSenderShard().getStatus());
                    assertEquals("success", r.getReceiverShard().getStatus());
                })
                .verifyComplete();
    }

    @Test
    void estimate() {
        mockNonce();

        var request = buildRequest(1.00, dataValueText);

        when(transactionInteractor.estimateTransactionCost(sendableCaptor.capture()))
                .thenReturn(Mono.just(TransactionCostEstimation.builder()
                        .transactionGasUnits("70000")
                        .build()));

        StepVerifier.create(sender.estimate(request))
                .assertNext(r -> {
                    var sendable = sendableCaptor.getValue();

                    assertEquals("1000000000000000000", sendable.getValue());
                    assertEquals(nonceValue, sendable.getNonce());
                    assertEquals(senderAddressBech32, sendable.getSender());
                    assertEquals(receiverAddressBech32, sendable.getReceiver());
                    assertNull(sendable.getGasLimit());
                    assertEquals(dataValueBase64, sendable.getData());

                    assertEquals("70000", r.getTransactionGasUnits());
                })
                .verifyComplete();
    }

    private void mockNonce() {
        when(accountInteractor.getNonce(any(Address.class)))
                .thenReturn(Mono.just(AddressNonce.builder().nonce(nonceValue).build()));
    }

    private TransactionRequest buildRequest(Double value, String data) {
        return TransactionRequest.builder()
                .wallet(wallet)
                .value(Balance.fromEgld(value))
                .receiverAddress(Address.fromBech32(receiverAddressBech32))
                .data(PayloadData.fromString(data))
                .build();
    }
}