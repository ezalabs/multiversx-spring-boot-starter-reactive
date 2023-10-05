package io.ezalabs.multiversxspringbootstarterreactive.interactor.smartcontract;

import io.ezalabs.multiversxspringbootstarterreactive.api.ApiResourceURI;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResultHex;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResultInt;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResultString;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.TransactionHash;
import io.ezalabs.multiversxspringbootstarterreactive.client.MxProxyClient;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.ContractFunction;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.ContractQuery;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.WrappedResponses;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

@AllArgsConstructor
public class MxSmartContractInteractorImpl implements MxSmartContractInteractor {

  private final MxProxyClient client;
  private final MxTransactionInteractor tInteractor;

  @Override
  public Mono<TransactionHash> callFunction(Wallet wallet, ContractFunction function) {
    return tInteractor.sendTransaction(
        wallet,
        TransactionRequest.builder()
            .receiverAddress(function.getSmartContractAddress())
            .data(function.getPayloadData())
            .value(function.getValue())
            .gasLimit(function.getGasLimit())
            .build());
  }

  @Override
  public Mono<ContractQueryResult> query(ContractQuery query) {
    return client
        .post(ApiResourceURI.QUERY_SMART_CONTRACT.getURI(),
            query.toSendable(),
            WrappedResponses.ScQueryResponse.class)
        .map(WrappedResponses.ScQueryResponse::getResult);
  }

  @Override
  public Mono<ContractQueryResultHex> queryHex(ContractQuery query) {
    return client
        .post(ApiResourceURI.QUERY_SMART_CONTRACT_HEX.getURI(),
            query.toSendable(),
            ContractQueryResultHex.class);
  }

  @Override
  public Mono<ContractQueryResultString> queryString(ContractQuery query) {
    return client
        .post(ApiResourceURI.QUERY_SMART_CONTRACT_STRING.getURI(),
            query.toSendable(),
            ContractQueryResultString.class);
  }

  @Override
  public Mono<ContractQueryResultInt> queryInt(ContractQuery query) {
    return client
        .post(ApiResourceURI.QUERY_SMART_CONTRACT_INT.getURI(),
            query.toSendable(),
            ContractQueryResultInt.class);
  }
}
