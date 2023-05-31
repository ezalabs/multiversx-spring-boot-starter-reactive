package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_BURN_CALL;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_MINT_CALL;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

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
    return type.equals(Type.MINT) ? ESDT_MINT_CALL : ESDT_BURN_CALL;
  }

  public enum Type {MINT, BURN}
}
