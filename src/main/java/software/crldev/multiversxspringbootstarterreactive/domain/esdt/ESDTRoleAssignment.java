package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_SET_ROLE_CALL;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_UNSET_ROLE_CALL;

import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

/**
 * Value object for ESDT role assignment
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTRoleAssignment implements ESDTTransaction {

  @NonNull
  Type type;
  @NonNull
  TokenIdentifier tokenIdentifier;
  @NonNull
  Address address;
  @NonNull
  Set<ESDTSpecialRole> roles;
  @NonNull
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultEsdtIssuance();

  private PayloadData processPayloadData() {
    if (roles.isEmpty()) {
      throw new IllegalArgumentException(ErrorMessage.TOKEN_ROLE.getValue());
    }

    return PayloadData.fromString(format("%s@%s@%s@%s",
        processType(),
        tokenIdentifier.getHex(),
        address.getHex(),
        roles.stream()
            .map(ESDTSpecialRole::getHex)
            .collect(Collectors.joining("@"))));
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

  private String processType() {
    return type.equals(Type.SET) ? ESDT_SET_ROLE_CALL : ESDT_UNSET_ROLE_CALL;
  }

  public enum Type {SET, UNSET}
}
