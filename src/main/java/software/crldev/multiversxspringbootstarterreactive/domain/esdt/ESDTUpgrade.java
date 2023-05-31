package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_UPGRADE_CALL;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenProperty;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

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

    return PayloadData.fromString(format("%s@%s%s",
        ESDT_UPGRADE_CALL,
        tokenIdentifier.getHex(),
        properties.stream()
            .map(p -> "@" + p.getNameHex() + "@" + p.getValueHex())
            .collect(Collectors.joining())));
  }

  @Override
  public TransactionRequest toTransactionRequest(Wallet wallet) {
    return TransactionRequest.builder()
        .receiverAddress(Address.fromBech32(ESDT_ISSUER_BECH32_ADDRESS))
        .data(processPayloadData())
        .value(Balance.zero())
        .gasLimit(gasLimit)
        .build();
  }
}
