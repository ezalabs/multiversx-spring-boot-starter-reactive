package software.crldev.elrondspringbootstarterreactive.interactor.smartcontract;

import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ContractFunction;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ContractQuery;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;

/**
 * Interface used for interaction with smart contracts on the network
 *
 * @author carlo_stanciu
 */
public interface ErdSmartContractInteractor {

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
