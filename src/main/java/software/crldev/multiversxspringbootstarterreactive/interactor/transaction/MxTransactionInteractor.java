package software.crldev.multiversxspringbootstarterreactive.interactor.transaction;

import java.util.List;
import reactor.core.publisher.Mono;
import software.crldev.multiversxspringbootstarterreactive.api.model.SimulationResults;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionCostEstimation;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionHash;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionOnNetwork;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionStatus;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionsSentResult;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;

/**
 * Interface used for interaction with Transactions on the network
 *
 * @author carlo_stanciu
 */
public interface MxTransactionInteractor {

  /**
   * Method used to send a transaction for execution
   *
   * @param payload - transaction in sendable format
   * @return - TransactionHash API response
   */
  Mono<TransactionHash> sendTransaction(Transaction.Sendable payload);

  /**
   * Method used to send a transaction for execution
   *
   * @param wallet  - transaction caller's Wallet instance
   * @param request - TransactionRequest instance with required data
   * @return - TransactionHash API response
   */
  Mono<TransactionHash> sendTransaction(Wallet wallet, TransactionRequest request);

  /**
   * Method used to send a batch of transactions for execution
   *
   * @param payload - a list of transactions in sendable format
   * @return - TransactionsSentResult API response
   */
  Mono<TransactionsSentResult> sendMultipleTransactions(List<Transaction.Sendable> payload);

  /**
   * Method used to send a batch of transactions for execution
   *
   * @param wallet  - transaction caller's Wallet instance
   * @param request -  a list of TransactionRequest
   * @return - TransactionsSentResult API response
   */
  Mono<TransactionsSentResult> sendMultipleTransactions(Wallet wallet, List<TransactionRequest> request);

  /**
   * Method used to send a transaction for simulation
   *
   * @param payload - transaction in sendable format
   * @return - SimulationResults API response
   */
  Mono<SimulationResults> simulateTransaction(Transaction.Sendable payload);

  /**
   * Method used to send a transaction for simulation
   *
   * @param wallet  - transaction caller's Wallet instance
   * @param request - object containing minimum required data
   * @return - SimulationResults API response
   */
  Mono<SimulationResults> simulateTransaction(Wallet wallet, TransactionRequest request);

  /**
   * Method used to send a transaction for cost querying
   *
   * @param payload - transaction in sendable format
   * @return - TransactionCostEstimation API response
   */
  Mono<TransactionCostEstimation> estimateTransactionCost(Transaction.Sendable payload);

  /**
   * Method used to send a transaction for cost querying
   *
   * @param wallet  - transaction caller's Wallet instance
   * @param request - object containing minimum required data
   * @return - TransactionCostEstimation API response
   */
  Mono<TransactionCostEstimation> estimateTransactionCost(Wallet wallet, TransactionRequest request);

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
