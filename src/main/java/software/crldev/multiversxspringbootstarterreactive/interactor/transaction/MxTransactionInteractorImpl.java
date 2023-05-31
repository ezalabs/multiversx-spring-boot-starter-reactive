package software.crldev.multiversxspringbootstarterreactive.interactor.transaction;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import software.crldev.multiversxspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.multiversxspringbootstarterreactive.api.model.AccountNonce;
import software.crldev.multiversxspringbootstarterreactive.api.model.SimulationResults;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionCostEstimation;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionHash;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionOnNetwork;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionStatus;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionsSentResult;
import software.crldev.multiversxspringbootstarterreactive.client.MxProxyClient;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.error.exception.InvalidSentTransactionsException;
import software.crldev.multiversxspringbootstarterreactive.error.exception.MissingTransactionRequestException;
import software.crldev.multiversxspringbootstarterreactive.error.exception.ProxyRequestException;
import software.crldev.multiversxspringbootstarterreactive.interactor.WrappedResponses;
import software.crldev.multiversxspringbootstarterreactive.interactor.account.MxAccountInteractor;

@AllArgsConstructor
public class MxTransactionInteractorImpl implements MxTransactionInteractor {

  private final MxProxyClient client;
  private final MxAccountInteractor accountInteractor;

  @Override
  public Mono<TransactionHash> sendTransaction(Transaction.Sendable payload) {
    return client
        .post(ApiResourceURI.SEND_TRANSACTION.getURI(),
            payload,
            TransactionHash.class);
  }

  @Override
  public Mono<TransactionHash> sendTransaction(Wallet wallet, TransactionRequest request) {
    return assignNonce(wallet)
        .map(nonce -> buildSendableFromRequest(wallet, request, nonce, false))
        .flatMap(this::sendTransaction);
  }

  @Override
  public Mono<TransactionsSentResult> sendMultipleTransactions(List<Transaction.Sendable> payloads) {
    return client
        .post(ApiResourceURI.SEND_MULTIPLE_TRANSACTIONS.getURI(),
            payloads.toArray(),
            TransactionsSentResult.class)
        .map(this::processSendTransactions);
  }

  @Override
  public Mono<TransactionsSentResult> sendMultipleTransactions(Wallet wallet, List<TransactionRequest> request) {
    request.stream().findFirst().orElseThrow(MissingTransactionRequestException::new);
    return assignNonce(wallet)
        .map(n -> {
          var listOfSendables = new ArrayList<Transaction.Sendable>();

          for (long i = 0, nonce = n; i < request.size() && nonce <= nonce + request.size(); i++, nonce++) {
            listOfSendables.add(buildSendableFromRequest(wallet, request.get((int) i), nonce, false));
          }

          return listOfSendables;
        })
        .flatMap(this::sendMultipleTransactions);
  }

  @Override
  public Mono<SimulationResults> simulateTransaction(Transaction.Sendable payload) {
    return client
        .post(ApiResourceURI.SIMULATE_TRANSACTION.getURI(), payload, WrappedResponses.SimulateTransactionResponse.class)
        .map(WrappedResponses.SimulateTransactionResponse::getResult);
  }

  @Override
  public Mono<SimulationResults> simulateTransaction(Wallet wallet, TransactionRequest request) {
    return assignNonce(wallet)
        .map(nonce -> buildSendableFromRequest(wallet, request, nonce, false))
        .flatMap(this::simulateTransaction);
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
  public Mono<TransactionCostEstimation> estimateTransactionCost(Wallet wallet, TransactionRequest request) {
    return assignNonce(wallet)
        .map(nonce -> buildSendableFromRequest(wallet, request, nonce, true))
        .flatMap(this::estimateTransactionCost);
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
    if (estimation.getTransactionGasUnits().equals("0")) {
      throw new ProxyRequestException(estimation.getReturnMessage());
    }

    return estimation;
  }

  private TransactionsSentResult processSendTransactions(TransactionsSentResult result) {
    if (result.getNumberOfSentTransactions() == 0) {
      throw new InvalidSentTransactionsException();
    }

    return result;
  }

  private Transaction.Sendable buildSendableFromRequest(Wallet wallet, TransactionRequest request, Long nonceValue,
      boolean isEstimationRequest) {
    var transaction = new Transaction();
    transaction.setValue(request.getValue());
    transaction.setNonce(Nonce.fromLong(nonceValue));
    transaction.setReceiver(request.getReceiverAddress());
    transaction.setSender(Address.fromHex(wallet.getPublicKeyHex()));
    transaction.setPayloadData(request.getData());
    transaction.setIsEstimation(isEstimationRequest);

    if (!request.getGasLimit().isZero()) {
      transaction.setGasLimit(request.getGasLimit());
    }

    wallet.sign(transaction);
    return transaction.toSendable();
  }

  private Mono<Long> assignNonce(Wallet wallet) {
    return accountInteractor.getNonce(Address
            .fromHex(wallet.getPublicKeyHex()))
        .map(AccountNonce::getNonce);
  }

}
