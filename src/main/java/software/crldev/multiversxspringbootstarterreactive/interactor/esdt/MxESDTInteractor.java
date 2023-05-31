package software.crldev.multiversxspringbootstarterreactive.interactor.esdt;

import java.util.Set;
import reactor.core.publisher.Mono;
import software.crldev.multiversxspringbootstarterreactive.api.model.AccountESDTRoles;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import software.crldev.multiversxspringbootstarterreactive.api.model.ESDTToken;
import software.crldev.multiversxspringbootstarterreactive.api.model.NFTData;
import software.crldev.multiversxspringbootstarterreactive.api.model.TokenList;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionHash;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTQueryType;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.ESDTTransaction;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;

/**
 * Interface used for interaction with ESDT tokens
 *
 * @author carlo_stanciu
 */
public interface MxESDTInteractor {

  /**
   * Method used to process specific ESDT Transactions
   *
   * @param wallet -
   * @return - TransactionHash API response
   */
  Mono<TransactionHash> processEsdtTransaction(Wallet wallet, ESDTTransaction transaction);

  /**
   * Method used to retrieve owned ESDT tokens
   *
   * @param address - token owner's address
   * @return - Set of ESDTToken API response
   */
  Mono<Set<ESDTToken>> getTokensForAccount(Address address);

  /**
   * Method used to retrieve ESDT roles
   *
   * @param address - roles owner's address
   * @return - AccountESDTRoles API response
   */
  Mono<AccountESDTRoles> getTokenRolesForAccount(Address address);

  /**
   * Method used to query list of ESDT tokens
   *
   * @param queryType - ESDT query type
   * @return - TokenList API response
   */
  Mono<TokenList> getAllTokens(ESDTQueryType queryType);

  /**
   * Method used to retrieve token properties
   *
   * @param identifier - ESDT token identifier
   * @return - ContractQueryResult API response
   */
  Mono<ContractQueryResult> getTokenProperties(TokenIdentifier identifier);

  /**
   * Method used to get roles for ESDT token
   *
   * @param identifier - ESDT token identifier
   * @return - ContractQueryResult API response
   */
  Mono<ContractQueryResult> getTokenSpecialRoles(TokenIdentifier identifier);

  /**
   * Method used to query an NFT's data
   *
   * @param address    - owner's address
   * @param identifier - NFT identifier
   * @param nonce      - NFT nonce
   * @return - NFTData API response
   */
  Mono<NFTData> getNftDataForAccount(Address address, TokenIdentifier identifier, Nonce nonce);

  /**
   * Method used to get list of NFT / SFT tokens for address
   *
   * @param address - owner's address
   * @return - TokenList API response
   */
  Mono<TokenList> getNftSftForAccount(Address address);

  /**
   * Method used to get list of NFT / SFT tokens with specific role for address
   *
   * @param address - owner's address
   * @param role    - token's role
   * @return - TokenList API response
   */
  Mono<TokenList> getTokensWithRole(Address address, ESDTSpecialRole role);

}
