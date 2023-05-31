package software.crldev.multiversxspringbootstarterreactive.interactor.transaction;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;

/**
 * Object used as a payload in the TransactionSender's methods
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class TransactionRequest {

  @NonNull
  Address receiverAddress;
  @NonNull
  Balance value;
  @NonNull
  PayloadData data;
  @NonNull
  @Builder.Default
  GasLimit gasLimit = GasLimit.zero();

}




