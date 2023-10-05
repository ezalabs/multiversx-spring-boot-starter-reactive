package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.FunctionArg;
import io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract.FunctionName;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;

/**
 * Value object for ESDT transfer
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTTransfer implements ESDTTransaction {

  @NonNull
  Address receiverAddress;
  @NonNull
  TokenIdentifier tokenIdentifier;
  @NonNull
  Balance amount;
  @NonNull
  @Builder.Default
  FunctionName functionName = FunctionName.empty();
  @NonNull
  @Builder.Default
  List<FunctionArg> args = Collections.emptyList();
  @NonNull
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultEsdtTransfer();

  private PayloadData processPayloadData() {
    var functionCall = functionName.isEmpty() ? Strings.EMPTY
        : "@" + functionName.getHex() +
            args.stream()
                .map(p -> "@" + p.getHex())
                .collect(Collectors.joining());

    return PayloadData.fromString(String.format("%s@%s@%s%s",
        ESDTConstants.ESDT_TRANSFER_CALL,
        tokenIdentifier.getHex(),
        amount.getHex(),
        functionCall));
  }

  @Override
  public TransactionRequest toTransactionRequest(Wallet wallet) {
    return TransactionRequest.builder()
        .receiverAddress(receiverAddress)
        .data(processPayloadData())
        .value(Balance.zero())
        .gasLimit(gasLimit)
        .build();
  }
}
