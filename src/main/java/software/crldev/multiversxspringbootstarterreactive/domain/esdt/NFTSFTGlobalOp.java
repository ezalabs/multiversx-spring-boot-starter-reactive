package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NFT_FREEZE_CALL;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NFT_UNFREEZE_CALL;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NFT_WIPE_CALL;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

/**
 * Value object for NFT \ SFT global operations
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class NFTSFTGlobalOp implements ESDTTransaction {

  @NonNull
  Type type;
  @NonNull
  TokenIdentifier tokenIdentifier;
  @NonNull
  Nonce nonce;
  @NonNull
  Address address;
  @NonNull
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultEsdtGlobalOp();

  private PayloadData processPayloadData() {
    return PayloadData.fromString(format("%s@%s@%s@%s",
        processType(),
        tokenIdentifier.getHex(),
        nonce.getHex(),
        address.getHex()));
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
    switch (type) {
      case FREEZE:
        return ESDT_NFT_FREEZE_CALL;
      case UNFREEZE:
        return ESDT_NFT_UNFREEZE_CALL;
      case WIPE:
        return ESDT_NFT_WIPE_CALL;
      default:
        throw new IllegalArgumentException();
    }
  }

  public enum Type {FREEZE, UNFREEZE, WIPE}
}
