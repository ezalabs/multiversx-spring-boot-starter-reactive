package software.crldev.multiversxspringbootstarterreactive.interactor.esdt;

import static java.lang.String.format;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_GET_PROPERTIES_CALL;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_GET_SPECIAL_ROLES_CALL;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import software.crldev.multiversxspringbootstarterreactive.api.ApiResourceURI;
import software.crldev.multiversxspringbootstarterreactive.api.model.AccountESDTRoles;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import software.crldev.multiversxspringbootstarterreactive.api.model.ESDTToken;
import software.crldev.multiversxspringbootstarterreactive.api.model.NFTData;
import software.crldev.multiversxspringbootstarterreactive.api.model.TokenList;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionHash;
import software.crldev.multiversxspringbootstarterreactive.client.MxProxyClient;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTQueryType;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTTransaction;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.smartcontract.ContractQuery;
import software.crldev.multiversxspringbootstarterreactive.domain.smartcontract.FunctionArg;
import software.crldev.multiversxspringbootstarterreactive.domain.smartcontract.FunctionName;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.WrappedResponses;
import software.crldev.multiversxspringbootstarterreactive.interactor.smartcontract.MxSmartContractInteractor;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.MxTransactionInteractor;

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
        .args(List.of(FunctionArg.fromString(identifier.getValue())))
        .build());
  }
}

