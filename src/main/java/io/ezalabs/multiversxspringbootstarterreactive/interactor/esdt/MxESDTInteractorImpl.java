package io.ezalabs.multiversxspringbootstarterreactive.interactor.esdt;

import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.api.ApiResourceURI;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.AccountESDTRoles;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.ESDTToken;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.NFTData;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.TokenList;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.TransactionHash;
import io.ezalabs.multiversxspringbootstarterreactive.client.MxProxyClient;
import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.smartcontract.MxSmartContractInteractor;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractor;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Nonce;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.ESDTQueryType;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.ESDTTransaction;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.ContractQuery;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.FunctionArg;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.FunctionName;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.WrappedResponses;

@AllArgsConstructor
public class MxESDTInteractorImpl implements MxESDTInteractor {

  private final MxProxyClient client;
  private final MxTransactionInteractor tInteractor;
  private final MxSmartContractInteractor scInteractor;

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
    return getProps(identifier, ESDTConstants.ESDT_GET_PROPERTIES_CALL);
  }

  @Override
  public Mono<ContractQueryResult> getTokenSpecialRoles(TokenIdentifier identifier) {
    return getProps(identifier, ESDTConstants.ESDT_GET_SPECIAL_ROLES_CALL);
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
        .smartContractAddress(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS))
        .functionName(FunctionName.fromString(scCall))
        .args(List.of(FunctionArg.fromString(identifier.getValue())))
        .build());
  }
}

