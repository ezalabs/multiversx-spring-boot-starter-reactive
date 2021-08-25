package software.crldev.elrondspringbootstarterreactive.sender;

import software.crldev.elrondspringbootstarterreactive.api.model.SimulationResults;
import software.crldev.elrondspringbootstarterreactive.api.model.TransactionCostEstimation;
import software.crldev.elrondspringbootstarterreactive.api.model.TransactionHash;
import software.crldev.elrondspringbootstarterreactive.api.model.TransactionsSentResult;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Interface used to send / simulate / estimate transactions
 * in a more simple approach using TransactionRequest.
 * All complexity of creating, estimating cost, assigning nonce and signing transaction
 * should be covered by the implementation.
 *
 * @author carlo_stanciu
 */
public interface ErdSimpleTransactionSender {

    /**
     * Method used to send a transaction for execution
     *
     * @param request - object containing minimum required data
     * @return - TransactionHash API response
     */
    Mono<TransactionHash> send(TransactionRequest request);

    /**
     * Method used to send a batch of transactions for execution
     *
     * @param request -  a list of TransactionRequest
     * @return - TransactionsSentResult API response
     */
    Mono<TransactionsSentResult> sendBatchOfTransactions(List<TransactionRequest> request);

    /**
     * Method used to send a transaction for simulation
     *
     * @param request - object containing minimum required data
     * @return - SimulationResults API response
     */
    Mono<SimulationResults> simulate(TransactionRequest request);

    /**
     * Method used to send a transaction for cost querying
     *
     * @param request - object containing minimum required data
     * @return - TransactionCostEstimation API response
     */
    Mono<TransactionCostEstimation> estimate(TransactionRequest request);

}
