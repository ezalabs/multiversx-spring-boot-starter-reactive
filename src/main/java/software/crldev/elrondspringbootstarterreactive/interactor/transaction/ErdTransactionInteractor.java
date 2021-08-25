package software.crldev.elrondspringbootstarterreactive.interactor.transaction;

import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.model.*;

import java.util.List;

/**
 * Interface used for interaction with Transactions on the network
 *
 * @author carlo_stanciu
 */
public interface ErdTransactionInteractor {

    /**
     * Method used to send a transaction for execution
     *
     * @param payload - transaction in sendable format
     * @return - TransactionHash API response
     */
    Mono<TransactionHash> sendTransaction(SendableTransaction payload);

    /**
     * Method used to send a batch of transactions for execution
     *
     * @param payload - a list of transactions in sendable format
     * @return - TransactionsSentResult API response
     */
    Mono<TransactionsSentResult> sendBatchOfTransactions(List<SendableTransaction> payload);

    /**
     * Method used to send a transaction for simulation
     *
     * @param payload - transaction in sendable format
     * @return - SimulationResults API response
     */
    Mono<SimulationResults> simulateTransaction(SendableTransaction payload);

    /**
     * Method used to send a transaction for cost querying
     *
     * @param payload - transaction in sendable format
     * @return - TransactionCostEstimation API response
     */
    Mono<TransactionCostEstimation> estimateTransactionCost(SendableTransaction payload);

    /**
     * Method used to query a Transaction's information
     *
     * @param transactionHash - value used as search id
     * @param withResults     - if true it fetches additional information such as smart contract results or receipts
     * @return - TransactionOnNetwork API response
     */
    Mono<TransactionOnNetwork> queryTransactionInfo(String transactionHash, boolean withResults);

    /**
     * Method used to query a Transaction's status
     *
     * @param transactionHash - value used as search id
     * @return - TransactionStatus API response
     */
    Mono<TransactionStatus> queryTransactionStatus(String transactionHash);

}
