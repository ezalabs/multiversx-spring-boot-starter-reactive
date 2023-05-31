package software.crldev.multiversxspringbootstarterreactive.interactor.smartcontract;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import software.crldev.multiversxspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResultHex;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResultInt;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResultString;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionHash;
import software.crldev.multiversxspringbootstarterreactive.client.MxProxyClient;
import software.crldev.multiversxspringbootstarterreactive.domain.smartcontract.ContractFunction;
import software.crldev.multiversxspringbootstarterreactive.domain.smartcontract.ContractQuery;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.WrappedResponses;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractor;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

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
