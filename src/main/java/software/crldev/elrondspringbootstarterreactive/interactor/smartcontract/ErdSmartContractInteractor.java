package software.crldev.elrondspringbootstarterreactive.interactor.smartcontract;

import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.model.*;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ScFunction;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.ScQuery;
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
     * @param wallet - transaction caller's Wallet instance
     * @param function - smart contract function object
     * @return - TransactionHash API response
     */
    Mono<TransactionHash> callFunction(Wallet wallet, ScFunction function);

    /**
     * Method used to query smart contract
     *
     * @return - ScQueryResult API response
     */
    Mono<ScQueryResult> query(Wallet wallet, ScQuery function);

    /**
     * Method used to query smart contract for hex value
     *
     * @param wallet - transaction caller's Wallet instance
     * @param function - smart contract query function object
     * @return - ScQueryResultHex API response
     */
    Mono<ScQueryResultHex> queryHex(Wallet wallet, ScQuery function);

    /**
     * Method used to query smart contract for String value
     *
     * @param wallet - transaction caller's Wallet instance
     * @param function - smart contract query function object
     * @return - ScQueryResultString API response
     */
    Mono<ScQueryResultString> queryString(Wallet wallet, ScQuery function);

    /**
     * Method used to query smart contract for integer value
     *
     * @param wallet - transaction caller's Wallet instance
     * @param function - smart contract query function object
     * @return - ScQueryResultInt API response
     */
    Mono<ScQueryResultInt> queryInt(Wallet wallet, ScQuery function);

}
