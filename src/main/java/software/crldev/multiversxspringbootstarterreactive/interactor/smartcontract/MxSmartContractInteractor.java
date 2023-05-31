package software.crldev.multiversxspringbootstarterreactive.interactor.smartcontract;

import reactor.core.publisher.Mono;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResultHex;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResultInt;
import software.crldev.multiversxspringbootstarterreactive.api.model.ContractQueryResultString;
import software.crldev.multiversxspringbootstarterreactive.api.model.TransactionHash;
import software.crldev.multiversxspringbootstarterreactive.domain.smartcontract.ContractFunction;
import software.crldev.multiversxspringbootstarterreactive.domain.smartcontract.ContractQuery;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;

/**
 * Interface used for interaction with smart contracts on the network
 *
 * @author carlo_stanciu
 */
public interface MxSmartContractInteractor {

  /**
   * Method used to call a smart contract function
   *
   * @param wallet   - transaction caller's Wallet instance
   * @param function - smart contract function object
   * @return - TransactionHash API response
   */
  Mono<TransactionHash> callFunction(Wallet wallet, ContractFunction function);

  /**
   * Method used to query smart contract
   *
   * @param query - smart contract query function object
   * @return - ScQueryResult API response
   */
  Mono<ContractQueryResult> query(ContractQuery query);

  /**
   * Method used to query smart contract for hex value
   *
   * @param query - smart contract query function object
   * @return - ScQueryResultHex API response
   */
  Mono<ContractQueryResultHex> queryHex(ContractQuery query);

  /**
   * Method used to query smart contract for String value
   *
   * @param query - smart contract query function object
   * @return - ScQueryResultString API response
   */
  Mono<ContractQueryResultString> queryString(ContractQuery query);

  /**
   * Method used to query smart contract for integer value
   *
   * @param query - smart contract query function object
   * @return - ScQueryResultInt API response
   */
  Mono<ContractQueryResultInt> queryInt(ContractQuery query);

}
