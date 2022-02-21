package software.crldev.elrondspringbootstarterreactive.interactor.esdt;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.client.ErdProxyClient;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTQueryType;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTTransaction;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ContractQuery;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionArgs;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionName;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.WrappedResponses;
import software.crldev.elrondspringbootstarterreactive.interactor.smartcontract.ErdSmartContractInteractor;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.ErdTransactionInteractor;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.*;

@AllArgsConstructor
public class ErdESDTInteractorImpl implements ErdESDTInteractor {

    private final ErdProxyClient client;
    private final ErdTransactionInteractor tInteractor;
    private final ErdSmartContractInteractor scInteractor;

    @Override
    public Mono<TransactionHash> processEsdtTransaction(Wallet wallet, ESDTTransaction transaction) {
        return tInteractor.sendTransaction(wallet, transaction.toTransactionRequest(wallet));
    }

    @Override
    public Mono<Set<ESDTToken>> getTokensForAccount(Address address) {
        return client
                .get(format(ApiResourceURI.ESDT_TOKENS_FOR_ADDRESS.getURI(), address.getBech32()),
                        WrappedResponses.AccountESDTsResponse.class)
                .map(WrappedResponses.AccountESDTsResponse::getResult)
                .map(Map::values)
                .map(HashSet::new);
    }

    @Override
    public Mono<AccountESDTRoles> getTokenRolesForAccount(Address address) {
        return client
                .get(format(ApiResourceURI.ESDT_ROLES_FOR_ADDRESS.getURI(), address.getBech32()),
                        AccountESDTRoles.class);
    }

    @Override
    public Mono<TokenList> getAllTokens(ESDTQueryType queryType) {
        String url;

        switch (queryType) {
            case ALL:
                url = ApiResourceURI.ESDT_ALL_ISSUED_TOKENS.getURI();
                break;
            case FUNGIBLE:
                url = ApiResourceURI.ESDT_ALL_ISSUED_FUNGIBLE_TOKENS.getURI();
                break;
            case NON_FUNGIBLE:
                url = ApiResourceURI.ESDT_ALL_ISSUED_NONFUNGIBLE_TOKENS.getURI();
                break;
            case SEMI_FUNGIBLE:
                url = ApiResourceURI.ESDT_ALL_ISSUED_SEMIFUNGIBLE_TOKENS.getURI();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + queryType);
        }

        return client.get(url, TokenList.class);
    }

    @Override
    public Mono<ContractQueryResult> getTokenProperties(TokenIdentifier identifier) {
        return getProps(identifier, ESDT_GET_PROPERTIES_CALL);
    }

    @Override
    public Mono<ContractQueryResult> getTokenSpecialRoles(TokenIdentifier identifier) {
        return getProps(identifier, ESDT_GET_SPECIAL_ROLES_CALL);
    }

    @Override
    public Mono<NFTData> getNftDataForAccount(Address address, TokenIdentifier identifier, Nonce nonce) {
        return client
                .get(format(ApiResourceURI.NFT_DATA_FOR_ADDRESS.getURI(),
                                address.getBech32(), identifier.getValue(), nonce.getValue()),
                        WrappedResponses.NFTDataResponse.class)
                .map(WrappedResponses.NFTDataResponse::getResult);
    }

    @Override
    public Mono<TokenList> getNftSftForAccount(Address address) {
        return client
                .get(format(ApiResourceURI.NFT_SFT_REGISTERED_FOR_ADDRESS.getURI(),
                                address.getBech32()),
                        TokenList.class);
    }

    @Override
    public Mono<TokenList> getTokensWithRole(Address address, ESDTSpecialRole role) {
        return client
                .get(format(ApiResourceURI.ESDT_TOKENS_WITH_ROLE.getURI(),
                                address.getBech32(), role.getValue()),
                        TokenList.class);
    }

    private Mono<ContractQueryResult> getProps(TokenIdentifier identifier, String scCall) {
        return scInteractor.query(ContractQuery
                .builder()
                .smartContractAddress(Address.fromBech32(ESDT_ISSUER_BECH32_ADDRESS))
                .functionName(FunctionName.fromString(scCall))
                .args(FunctionArgs.fromString(identifier.getValue()))
                .build());
    }
}

