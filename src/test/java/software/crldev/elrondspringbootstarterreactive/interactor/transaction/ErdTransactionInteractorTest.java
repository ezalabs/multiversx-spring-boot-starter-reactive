package software.crldev.elrondspringbootstarterreactive.interactor.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import software.crldev.elrondspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.error.exception.ProxyRequestException;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;
import software.crldev.elrondspringbootstarterreactive.interactor.account.ErdAccountInteractor;

import java.math.BigInteger;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static software.crldev.elrondspringbootstarterreactive.config.CurrencyConstants.ONE_EGLD_STRING;
import static software.crldev.elrondspringbootstarterreactive.interactor.Helper.verifyInteractionException;
import static software.crldev.elrondspringbootstarterreactive.interactor.Helper.verifyInteractionOk;

@ExtendWith(MockitoExtension.class)
class ErdTransactionInteractorTest {

    ErdTransactionInteractor interactor;

    @Mock
    ErdProxyClient client;
    @Mock
    ErdAccountInteractor accountInteractor;
    @Captor
    ArgumentCaptor<Transaction.Sendable> sendableCaptor;
    @Captor
    ArgumentCaptor<Object[]> sendablesCaptor;

    Address senderAddress = Address.fromBech32("erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj");
    String hash = "699ae03e6f9a18cb8b1f131b061a46a8b7dd96dfa3fe24861f03aa824a462920";
    Transaction.Sendable sendable = new Transaction().toSendable();

    String senderAddressBech32 = "erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj";
    String receiverAddressBech32 = "erd1gklqdv77my5y8n75hszv737gq54q9xk0tmzdh8v5vkfstd64aw7ser9nfr";
    Wallet wallet = Wallet.fromPrivateKeyHex("8442d0bcadbae1b75eff1165f1e3a61f120bddbb440393d8ba3c366342ee4f62");
    Long nonceValue = 10L;
    String dataValueText = "crldev x elrond";
    String dataValueBase64 = Base64.getEncoder().encodeToString(dataValueText.getBytes());

    @BeforeEach
    void setUp() {
        interactor = new ErdTransactionInteractorImpl(client, accountInteractor);
    }

    @Test
    void sendTransaction_withSendableArg() {
        var apiResponse = TransactionHash.builder().hash(hash).build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.sendTransaction(new Transaction().toSendable()),
                (r) -> assertEquals(hash, r.getHash()), HttpMethod.POST);
    }

    @Test
    void sendTransaction_withTransactionRequest() {
        mockNonce();

        var request = buildRequest(1.00, dataValueText);

        when(client.post(eq(ApiResourceURI.SEND_TRANSACTION.getURI()), sendableCaptor.capture(), eq(TransactionHash.class)))
                .thenReturn(Mono.just(TransactionHash.builder().hash("1234").build()));

        StepVerifier.create(interactor.sendTransaction(wallet, request))
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
    void sendMultipleTransactions_withSendableArg() {
        var apiResponse = TransactionsSentResult.builder()
                .numberOfSentTransactions(2)
                .transactionsHashes(new HashMap<>() {{
                    put("0", "2352523");
                    put("1", "43093403");
                }})
                .build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.sendMultipleTransactions(List.of(new Transaction().toSendable(), new Transaction().toSendable())),
                (r) -> {
                    assertEquals(2, r.getNumberOfSentTransactions());
                    assertEquals("2352523", r.getTransactionsHashes().get("0"));
                    assertEquals("43093403", r.getTransactionsHashes().get("1"));
                }, HttpMethod.POST);
    }

    @Test
    void sendMultipleTransactions_withTransactionRequest() {
        mockNonce();

        var dataValueText2 = "hello world";
        var dataValueBase64_2 = Base64.getEncoder().encodeToString(dataValueText2.getBytes());

        var request1 = buildRequest(1.00, dataValueText);
        var request2 = buildRequest(2.00, dataValueText2);

        var listOfRequests = List.of(request1, request2);

        when(client.post(eq(ApiResourceURI.SEND_MULTIPLE_TRANSACTIONS.getURI()), sendablesCaptor.capture(), eq(TransactionsSentResult.class)))
                .thenReturn(Mono.just(TransactionsSentResult.builder()
                        .numberOfSentTransactions(2)
                        .transactionsHashes(new HashMap<>() {{
                            put("0", "2352523");
                            put("1", "43093403");
                        }})
                        .build()));

        StepVerifier.create(interactor.sendMultipleTransactions(wallet, listOfRequests))
                .assertNext(r -> {
                    var listOfSendables = sendablesCaptor.getValue();

                    var sendable1 = (Transaction.Sendable) listOfSendables[0];
                    var sendable2 = (Transaction.Sendable) listOfSendables[1];

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
    void simulateTransaction_withSendableArg() {
        var apiResponse = WrappedResponses.SimulateTransactionResponse
                .builder()
                .result(SimulationResults
                        .builder()
                        .senderShard(ShardFromSimulatedTransaction.builder().hash(hash).build())
                        .build())
                .build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.simulateTransaction(sendable),
                (r) -> assertEquals(hash, r.getSenderShard().getHash()), HttpMethod.POST);
    }

    @Test
    void simulateTransaction_withTransactionRequest() {
        mockNonce();

        var request = buildRequest(1.00, dataValueText);

        when(client.post(eq(ApiResourceURI.SIMULATE_TRANSACTION.getURI()), sendableCaptor.capture(), eq(WrappedResponses.SimulateTransactionResponse.class)))
                .thenReturn(Mono.just(WrappedResponses.SimulateTransactionResponse.builder()
                                        .result(SimulationResults.builder()
                                                .senderShard(ShardFromSimulatedTransaction.builder()
                                                        .status("success")
                                                        .build())
                                                .receiverShard(ShardFromSimulatedTransaction.builder()
                                                        .status("success")
                                                        .build())
                                                .build())
                        .build()));

        StepVerifier.create(interactor.simulateTransaction(wallet, request))
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
    void estimateTransactionCost() {
        var returnMessage = "success";
        var txGasUnits = "12345";

        var apiResponse = TransactionCostEstimation
                .builder()
                .returnMessage(returnMessage)
                .transactionGasUnits(txGasUnits)
                .build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.estimateTransactionCost(sendable),
                (r) -> {
                    assertEquals(returnMessage, r.getReturnMessage());
                    assertEquals(txGasUnits, r.getTransactionGasUnits());
                }, HttpMethod.POST);
    }

    @Test
    void estimateTransactionCost_withTransactionRequest() {
        mockNonce();

        var request = buildRequest(1.00, dataValueText);

        when(client.post(eq(ApiResourceURI.ESTIMATE_TRANSACTION_COST.getURI()), sendableCaptor.capture(), eq(TransactionCostEstimation.class)))
                .thenReturn(Mono.just(TransactionCostEstimation.builder()
                        .transactionGasUnits("70000")
                        .build()));

        StepVerifier.create(interactor.estimateTransactionCost(wallet, request))
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

    @Test
    void estimateTransactionCost_fail() {
        var returnMessage = "It didn't work man";
        var txGasUnits = "0";

        var apiResponse = TransactionCostEstimation
                .builder()
                .returnMessage(returnMessage)
                .transactionGasUnits(txGasUnits)
                .build();

        verifyInteractionException(
                client,
                apiResponse,
                () -> interactor.estimateTransactionCost(sendable),
                ProxyRequestException.class, HttpMethod.POST);
    }

    @Test
    void queryTransaction() {
        var addressBech32 = senderAddress.getBech32();
        var value = new BigInteger(ONE_EGLD_STRING);
        var nonce = 10L;

        var apiResponse = WrappedResponses.QueryTransactionResponse
                .builder()
                .transaction(TransactionOnNetwork
                        .builder()
                        .sender(addressBech32)
                        .nonce(nonce)
                        .value(value)
                        .nonce(nonce)
                        .build())
                .build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.queryTransactionInfo("91j0fj2f3", true),
                (r) -> {
                    assertEquals(addressBech32, r.getSender());
                    assertEquals(value, r.getValue());
                    assertEquals(nonce, r.getNonce());
                }, HttpMethod.GET);
    }

    @Test
    void queryTransactionStatus() {
        var status = "success";

        var apiResponse = TransactionStatus
                .builder()
                .status(status)
                .build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.queryTransactionStatus("91j0fj2f3"),
                (r) -> assertEquals(status, r.getStatus()), HttpMethod.GET);
    }

    private void mockNonce() {
        when(accountInteractor.getNonce(any(Address.class)))
                .thenReturn(Mono.just(AddressNonce.builder().nonce(nonceValue).build()));
    }

    private TransactionRequest buildRequest(Double value, String data) {
        return TransactionRequest.builder()
                .value(Balance.fromEgld(value))
                .receiverAddress(Address.fromBech32(receiverAddressBech32))
                .data(PayloadData.fromString(data))
                .build();
    }
}