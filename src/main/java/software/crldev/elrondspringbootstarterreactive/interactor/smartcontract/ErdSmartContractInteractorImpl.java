package software.crldev.elrondspringbootstarterreactive.interactor.smartcontract;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ContractFunction;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ContractQuery;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

@AllArgsConstructor
public class ErdSmartContractInteractorImpl implements ErdSmartContractInteractor {

    private final ErdProxyClient client;
    private final ErdTransactionInteractor tInteractor;

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
