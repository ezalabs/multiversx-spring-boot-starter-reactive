package software.crldev.elrondspringbootstarterreactive.interactor.transaction;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.elrondspringbootstarterreactive.error.exception.InvalidSentTransactionsException;
import software.crldev.elrondspringbootstarterreactive.error.exception.ProxyRequestException;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;

import java.util.List;

import static java.lang.String.format;

@AllArgsConstructor
public class ErdTransactionInteractorImpl implements ErdTransactionInteractor {

    private final ErdProxyClient client;

    @Override
    public Mono<TransactionHash> sendTransaction(Transaction.Sendable payload) {
        return client
                .post(ApiResourceURI.SEND_TRANSACTION.getURI(),
                        payload,
                        TransactionHash.class);
    }

    @Override
    public Mono<TransactionsSentResult> sendBatchOfTransactions(List<Transaction.Sendable> payloads) {
        return client
                .post(ApiResourceURI.SEND_MULTIPLE_TRANSACTIONS.getURI(),
                        payloads.toArray(),
                        TransactionsSentResult.class)
                .map(this::processSendTransactions);
    }

    @Override
    public Mono<SimulationResults> simulateTransaction(Transaction.Sendable payload) {
        return client
                .post(ApiResourceURI.SIMULATE_TRANSACTION.getURI(), payload, WrappedResponses.SimulateTransactionResponse.class)
                .map(WrappedResponses.SimulateTransactionResponse::getResult);
    }

    @Override
    public Mono<TransactionCostEstimation> estimateTransactionCost(Transaction.Sendable payload) {
        return client
                .post(ApiResourceURI.ESTIMATE_TRANSACTION_COST.getURI(),
                        payload,
                        TransactionCostEstimation.class)
                .map(this::processCostEstimation);
    }

    @Override
    public Mono<TransactionOnNetwork> queryTransactionInfo(String transactionHash, boolean withResults) {
        return client
                .get(format(ApiResourceURI.TRANSACTION_ON_NETWORK.getURI(), transactionHash, withResults),
                        WrappedResponses.QueryTransactionResponse.class)
                .map(WrappedResponses.QueryTransactionResponse::getTransaction);
    }

    @Override
    public Mono<TransactionStatus> queryTransactionStatus(String transactionHash) {
        return client
                .get(format(ApiResourceURI.TRANSACTION_STATUS.getURI(), transactionHash),
                        TransactionStatus.class);
    }

    private TransactionCostEstimation processCostEstimation(TransactionCostEstimation estimation) {
        if (estimation.getTransactionGasUnits().equals("0"))
            throw new ProxyRequestException(estimation.getReturnMessage());

        return estimation;
    }

    private TransactionsSentResult processSendTransactions(TransactionsSentResult result) {
        if (result.getNumberOfSentTransactions() == 0)
            throw new InvalidSentTransactionsException();

        return result;
    }

}
