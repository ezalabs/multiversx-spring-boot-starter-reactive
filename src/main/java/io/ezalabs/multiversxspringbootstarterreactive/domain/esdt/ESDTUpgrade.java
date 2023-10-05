package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenProperty;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Value object for ESDT upgrading
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTUpgrade implements ESDTTransaction {

  @NonNull
  TokenIdentifier tokenIdentifier;
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultEsdtIssuance();
  @Builder.Default
  Set<TokenProperty> properties = new HashSet<>();

  private PayloadData processPayloadData() {
    if (properties.isEmpty()) {
      throw new IllegalArgumentException(ErrorMessage.TOKEN_PROPERTY.getValue());
    }

    return PayloadData.fromString(String.format("%s@%s%s",
        ESDTConstants.ESDT_UPGRADE_CALL,
        tokenIdentifier.getHex(),
        properties.stream()
            .map(p -> "@" + p.getNameHex() + "@" + p.getValueHex())
            .collect(Collectors.joining())));
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
}
