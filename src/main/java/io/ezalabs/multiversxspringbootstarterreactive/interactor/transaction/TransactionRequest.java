package io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;

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




