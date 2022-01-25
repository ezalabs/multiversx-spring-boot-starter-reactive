package software.crldev.elrondspringbootstarterreactive.interactor.esdt;

import reactor.core.publisher.Mono;
import software.crldev.elrondspringbootstarterreactive.api.model.TransactionHash;

/**
 * Interface used for interaction with ESDT tokens
 *
 * @author carlo_stanciu
 */
public interface ESDTInteractor {

    Mono<TransactionHash> issueEsdt();

    Mono<TransactionHash> transferEsdt();

    Mono<TransactionHash> transferEsdtWithScCall();

}
