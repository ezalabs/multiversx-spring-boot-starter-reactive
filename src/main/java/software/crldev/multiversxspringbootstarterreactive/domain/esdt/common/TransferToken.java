package software.crldev.multiversxspringbootstarterreactive.domain.esdt.common;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;

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
