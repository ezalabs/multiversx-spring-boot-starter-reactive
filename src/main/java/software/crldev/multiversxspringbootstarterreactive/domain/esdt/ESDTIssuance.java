package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static io.netty.util.internal.StringUtil.EMPTY_STRING;
import static java.lang.String.format;
import static java.util.Objects.nonNull;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_FUNGIBLE_ISSUANCE_CALL;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_ISSUANCE_EGLD_COST;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NON_FUNGIBLE_ISSUE_CALL;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_SEMI_FUNGIBLE_ISSUE_CALL;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.META_ESDT_ISSUE_CALL;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenDecimals;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenInitialSupply;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenName;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenProperty;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenPropertyName;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenTicker;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

/**
 * Value object for ESDT issuance (FUNGIBLE, SEMI-FUNGIBLE, NON-FUNGIBLE, META)
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTIssuance implements ESDTTransaction {

  @NonNull
  Type type;
  @NonNull
  TokenName tokenName;
  @NonNull
  TokenTicker tokenTicker;
  TokenInitialSupply initialSupply;
  TokenDecimals decimals;
  @Builder.Default
  Balance value = Balance.fromNumber(ESDT_ISSUANCE_EGLD_COST);
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultEsdtIssuance();
  @Builder.Default
  Set<TokenProperty> properties = new HashSet<>();

  private PayloadData processPayloadData() {
    return PayloadData.fromString(format("%s@%s@%s%s%s%s",
        processType(),
        tokenName.getHex(),
        tokenTicker.getHex(),
        processSupplyArg(),
        processDecimalsArg(),
        processPropsArg()));
  }

  @Override
  public TransactionRequest toTransactionRequest(Wallet wallet) {
    return TransactionRequest.builder()
        .receiverAddress(Address.fromBech32(ESDT_ISSUER_BECH32_ADDRESS))
        .data(processPayloadData())
        .value(value)
        .gasLimit(gasLimit)
        .build();
  }

  private String processSupplyArg() {
    if (!type.equals(Type.FUNGIBLE)) {
      return EMPTY_STRING;
    }
    return "@" + initialSupply.getHex();
  }

  private String processDecimalsArg() {
    if (!(type.equals(Type.FUNGIBLE) || type.equals(Type.META))) {
      return EMPTY_STRING;
    }
    return "@" + decimals.getHex();
  }

  private String processPropsArg() {
    properties.removeIf(p -> type.equals(Type.FUNGIBLE) && p.getName()
        .equals(TokenPropertyName.CAN_TRANSFER_NFT_CREATE_ROLE));
    properties.removeIf(p ->
        (type.equals(Type.SEMI_FUNGIBLE) ||
            type.equals(Type.NON_FUNGIBLE) ||
            type.equals(Type.META))
            &&
            (p.getName().equals(TokenPropertyName.CAN_UPGRADE) ||
                p.getName().equals(TokenPropertyName.CAN_ADD_SPECIAL_ROLES) ||
                p.getName().equals(TokenPropertyName.CAN_CHANGE_OWNER)));

    return properties.stream()
        .map(p -> "@" + p.getNameHex() + "@" + p.getValueHex())
        .collect(Collectors.joining());
  }

  private String processType() {
    switch (type) {
      case FUNGIBLE:
        throwIfNull(initialSupply, ErrorMessage.INITIAL_SUPPLY.getValue());
        throwIfNull(decimals, ErrorMessage.TOKEN_DECIMALS.getValue());
        return ESDT_FUNGIBLE_ISSUANCE_CALL;
      case SEMI_FUNGIBLE:
        return ESDT_SEMI_FUNGIBLE_ISSUE_CALL;
      case NON_FUNGIBLE:
        return ESDT_NON_FUNGIBLE_ISSUE_CALL;
      case META:
        throwIfNull(decimals, ErrorMessage.TOKEN_DECIMALS.getValue());
        return META_ESDT_ISSUE_CALL;
      default:
        throw new IllegalArgumentException();
    }
  }

  private void throwIfNull(Object target, String message) {
    if (!nonNull(target)) {
      throw new IllegalArgumentException(message);
    }
  }

  public enum Type {FUNGIBLE, SEMI_FUNGIBLE, NON_FUNGIBLE, META}
}
