package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
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
 * Value object for NFT create role transfer
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class NFTCreationRoleTransfer implements ESDTTransaction {

  @NonNull
  TokenIdentifier tokenIdentifier;
  @NonNull
  Address fromAddress;
  @NonNull
  Address toAddress;
  @NonNull
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultEsdtIssuance();

  private PayloadData processPayloadData() {
    return PayloadData.fromString(String.format("%s@%s@%s@%s",
        ESDTConstants.ESDT_NFT_CREATE_ROLE_TRANSFER_CALL,
        tokenIdentifier.getHex(),
        fromAddress.getHex(),
        toAddress.getHex()));
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
