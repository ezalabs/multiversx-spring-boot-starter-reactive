package io.ezalabs.multiversxspringbootstarterreactive.interactor.account;

import io.ezalabs.multiversxspringbootstarterreactive.api.model.AccountBalance;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.AccountNonce;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.AccountOnNetwork;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.AccountStorageValue;
import io.ezalabs.multiversxspringbootstarterreactive.api.model.TransactionForAddress;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Mono;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;

/**
 * Interface used for interaction with Account on the network
 *
 * @author carlo_stanciu
 */
public interface MxAccountInteractor {

  /**
   * Method used to query an Account's information
   *
   * @param address - Address input
   * @return - AccountOnNetwork API response
   */
  Mono<AccountOnNetwork> getAccountInfo(Address address);

  /**
   * Method used to query the balance of an Account
   *
   * @param address - Address input
   * @return - AddressBalance API response
   */
  Mono<AccountBalance> getBalance(Address address);

  /**
   * Method used to query the nonce of an Account
   *
   * @param address - Address input
   * @return - AddressNonce API response
   */
  Mono<AccountNonce> getNonce(Address address);

  /**
   * Method used to query the list of transactions for an Account
   *
   * @param address - Address input
   * @return - TransactionForAddress API response
   */
  Mono<List<TransactionForAddress>> getTransactions(Address address);

  /**
   * Method used to query a storage value of an Account
   *
   * @param address - Address input
   * @param key     - String key of the value
   * @return - AddressStorageValue API Response
   */
  Mono<AccountStorageValue> getStorageValue(Address address, String key);

  /**
   * Method used to query all the storage of an Account
   *
   * @param address - Address input
   * @return - storage as a Map of String keys and String values
   */
  Mono<Map<String, String>> getStorage(Address address);


}
