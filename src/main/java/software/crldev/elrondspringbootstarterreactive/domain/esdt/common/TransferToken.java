package software.crldev.elrondspringbootstarterreactive.domain.esdt.common;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;

/**
 * Value object for ESDT token data for transfer
 *
 * @author carlo_stanciu
 */
@Value
@Builder
public class TransferToken {

    @NonNull
    TokenIdentifier identifier;
    @NonNull
    @Builder.Default
    Nonce nonce = Nonce.fromLong(0L);
    @NonNull
    Balance amount;

}
