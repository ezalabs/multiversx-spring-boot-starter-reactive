package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Nonce;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
        .receiverAddress(Address.fromBech32(ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS))
        .data(processPayloadData())
        .value(Balance.zero())
        .gasLimit(gasLimit)
        .build();
  }

  private String processType() {
    switch (type) {
      case FREEZE:
        return ESDTConstants.ESDT_NFT_FREEZE_CALL;
      case UNFREEZE:
        return ESDTConstants.ESDT_NFT_UNFREEZE_CALL;
      case WIPE:
        return ESDTConstants.ESDT_NFT_WIPE_CALL;
      default:
        throw new IllegalArgumentException();
    }
  }

  public enum Type {FREEZE, UNFREEZE, WIPE}
}
