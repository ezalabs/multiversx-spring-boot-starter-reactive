package software.crldev.elrondspringbootstarterreactive.interactor.smartcontract;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.config.TransactionConstants;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ScFunction;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ScQuery;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

/**
 * Interface used for interaction with smart contracts on the network
 *
 * @author carlo_stanciu
 */
@AllArgsConstructor
public class ErdSmartContractInteractorImpl implements ErdSmartContractInteractor {

    private final ErdProxyClient client;
    private final ErdTransactionInteractor tInteractor;

    @Override
    public Mono<TransactionHash> callFunction(Wallet wallet, ScFunction function) {
        return tInteractor.sendTransaction(
                wallet,
                TransactionRequest.builder()
                        .receiverAddress(function.getSmartContractAddress())
                        .data(function.getPayloadData())
                        .value(function.getValue())
                        .gasLimit(GasLimit.fromNumber(TransactionConstants.SC_CALL_GAS_LIMIT))
                        .build());
    }

    @Override
    public Mono<ScQueryResult> query(Wallet wallet, ScQuery function) {
        return client
                .post(ApiResourceURI.QUERY_SMART_CONTRACT.getURI(),
                        function.toSendable(),
                        WrappedResponses.ScQueryResponse.class)
                .map(WrappedResponses.ScQueryResponse::getResult);
    }

    @Override
    public Mono<ScQueryResultHex> queryHex(Wallet wallet, ScQuery function) {
        return client
                .post(ApiResourceURI.QUERY_SMART_CONTRACT_HEX.getURI(),
                        function.toSendable(),
                        ScQueryResultHex.class);
    }

    @Override
    public Mono<ScQueryResultString> queryString(Wallet wallet, ScQuery function) {
        return client
                .post(ApiResourceURI.QUERY_SMART_CONTRACT_STRING.getURI(),
                        function.toSendable(),
                        ScQueryResultString.class);
    }

    @Override
    public Mono<ScQueryResultInt> queryInt(Wallet wallet, ScQuery function) {
        return client
                .post(ApiResourceURI.QUERY_SMART_CONTRACT_INT.getURI(),
                        function.toSendable(),
                        ScQueryResultInt.class);
    }
}
