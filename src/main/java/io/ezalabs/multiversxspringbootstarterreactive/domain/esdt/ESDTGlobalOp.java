package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static io.netty.util.internal.StringUtil.EMPTY_STRING;
import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Value object for ESDT global operations
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTGlobalOp implements ESDTTransaction {

  @NonNull
  Type type;
  @NonNull
  TokenIdentifier tokenIdentifier;
  @NonNull
  @Builder.Default
  Address targetAddress = Address.zero();
  @NonNull
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultEsdtIssuance();

  private PayloadData processPayloadData() {
    return PayloadData.fromString(format("%s@%s%s",
        processType(),
        tokenIdentifier.getHex(),
        processAddressArg()
    ));
  }

  @Override
  public TransactionRequest toTransactionRequest(Wallet wallet) {
    return TransactionRequest.builder()
        .receiverAddress(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS))
        .data(processPayloadData())
        .value(Balance.zero())
        .gasLimit(gasLimit)
        .build();
  }

  private String processAddressArg() {
    if (type.equals(Type.PAUSE) || type.equals(Type.UNPAUSE)) {
      return EMPTY_STRING;
    }
    return "@" + targetAddress.getHex();
  }

  private String processType() {
    switch (type) {
      case FREEZE:
        throwIfAddressEmpty();
        return ESDTConstants.ESDT_FREEZE_CALL;
      case UNFREEZE:
        throwIfAddressEmpty();
        return ESDTConstants.ESDT_UNFREEZE_CALL;
      case PAUSE:
        return ESDTConstants.ESDT_PAUSE_CALL;
      case UNPAUSE:
        return ESDTConstants.ESDT_UNPAUSE_CALL;
      case WIPE:
        throwIfAddressEmpty();
        return ESDTConstants.ESDT_WIPE_CALL;
      default:
        throw new IllegalArgumentException();
    }
  }

  private void throwIfAddressEmpty() {
    if (targetAddress.isZero()) {
      throw new IllegalArgumentException(ErrorMessage.ADDRESS_EMPTY.getValue());
    }
  }

  public enum Type {PAUSE, UNPAUSE, FREEZE, UNFREEZE, WIPE}
}
