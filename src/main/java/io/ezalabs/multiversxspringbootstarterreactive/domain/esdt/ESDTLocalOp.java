package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Value object for ESDT Local operations minting
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTLocalOp implements ESDTTransaction {

  @NonNull
  Type type;
  @NonNull
  TokenIdentifier tokenIdentifier;
  @NonNull
  Balance amount;
  @NonNull
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultEsdtLocalOp();

  private PayloadData processPayloadData() {
    return PayloadData.fromString(format("%s@%s@%s",
        processType(),
        tokenIdentifier.getHex(),
        amount.getHex()));
  }

  @Override
  public TransactionRequest toTransactionRequest(Wallet wallet) {
    return TransactionRequest.builder()
        .receiverAddress(wallet.getAddress())
        .data(processPayloadData())
        .value(Balance.zero())
        .gasLimit(gasLimit)
        .build();
  }

  private String processType() {
    return type.equals(Type.MINT) ? ESDTConstants.ESDT_MINT_CALL : ESDTConstants.ESDT_BURN_CALL;
  }

  public enum Type {MINT, BURN}
}
