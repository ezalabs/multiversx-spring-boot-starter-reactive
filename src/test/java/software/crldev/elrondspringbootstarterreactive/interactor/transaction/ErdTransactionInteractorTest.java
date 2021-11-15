package software.crldev.elrondspringbootstarterreactive.interactor.transaction;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.elrondspringbootstarterreactive.error.exception.ProxyRequestException;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static software.crldev.elrondspringbootstarterreactive.config.CurrencyConstants.ONE_EGLD_STRING;
import static software.crldev.elrondspringbootstarterreactive.interactor.Helper.verifyInteractionException;
import static software.crldev.elrondspringbootstarterreactive.interactor.Helper.verifyInteractionOk;

@ExtendWith(MockitoExtension.class)
class ErdTransactionInteractorTest {

    ErdTransactionInteractor interactor;

    @Mock
    ErdProxyClient client;

    Address senderAddress = Address.fromBech32("erd1h7r2m9c250yncguz3zwq5na6gu5ttwz3vdx40nxkthxaak5v3wcqtpkvkj");
    String hash = "699ae03e6f9a18cb8b1f131b061a46a8b7dd96dfa3fe24861f03aa824a462920";
    Transaction.Sendable sendable = new Transaction().toSendable();

    @BeforeEach
    void setUp() {
        interactor = new ErdTransactionInteractorImpl(client);
    }

    @Test
    void sendTransaction() {
        var apiResponse = TransactionHash.builder().hash(hash).build();

        verifyInteractionOk(
                client,
                apiResponse,
                () -> interactor.sendTransaction(new Transaction().toSendable()),
                (r) -> assertEquals(hash, r.getHash()), HttpMethod.POST);
    }

    @Test
    void sendMultipleTransactions() {
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
                () -> interactor.sendBatchOfTransactions(List.of(new Transaction().toSendable(), new Transaction().toSendable())),
                (r) -> {
                    assertEquals(2, r.getNumberOfSentTransactions());
                    assertEquals("2352523", r.getTransactionsHashes().get("0"));
                    assertEquals("43093403", r.getTransactionsHashes().get("1"));
                }, HttpMethod.POST);
    }

    @Test
    void simulateTransaction() {
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
}