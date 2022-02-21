package software.crldev.elrondspringbootstarterreactive.interactor.account;

import software.crldev.elrondspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

import static java.lang.String.format;

@AllArgsConstructor
public class ErdAccountInteractorImpl implements ErdAccountInteractor {

    private final ErdProxyClient client;

    @Override
    public Mono<AccountOnNetwork> getAccountInfo(Address address) {
        return client
                .get(String.format(ApiResourceURI.ACCOUNT_ON_NETWORK.getURI(), address.getBech32()), WrappedResponses.GetAccountWrapper.class)
                .map(WrappedResponses.GetAccountWrapper::getAccount);
    }

    @Override
    public Mono<AccountBalance> getBalance(Address address) {
        return client
                .get(format(ApiResourceURI.ACCOUNT_BALANCE.getURI(), address.getBech32()),
                        AccountBalance.class);
    }

    @Override
    public Mono<AccountNonce> getNonce(Address address) {
        return client
                .get(format(ApiResourceURI.ACCOUNT_NONCE.getURI(), address.getBech32()),
                        AccountNonce.class);
    }

    @Override
    public Mono<List<TransactionForAddress>> getTransactions(Address address) {
        return client
                .get(format(ApiResourceURI.ADDRESS_TRANSACTIONS.getURI(), address.getBech32()),
                        WrappedResponses.GetAccountTransactionsWrapper.class)
                .map(WrappedResponses.GetAccountTransactionsWrapper::getList);
    }

    @Override
    public Mono<AccountStorageValue> getStorageValue(Address address, String key) {
        return client
                .get(format(ApiResourceURI.ACCOUNT_STORAGE_VALUE.getURI(), address.getBech32(), key),
                        AccountStorageValue.class);
    }

    @Override
    public Mono<Map<String, String>> getStorage(Address address) {
        return client
                .get(format(ApiResourceURI.ACCOUNT_STORAGE.getURI(), address.getBech32()),
                        WrappedResponses.GetAllAccountStorageWrapper.class)
                .map(WrappedResponses.GetAllAccountStorageWrapper::getPairs);
    }
}
