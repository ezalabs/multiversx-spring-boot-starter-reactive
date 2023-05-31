package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NFT_UPDATE_ATTRIBUTES_CALL;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenAttributes;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

/**
 * Value object for NFT attributes update
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class NFTAttributesUpdate implements ESDTTransaction {

  @NonNull
  TokenIdentifier tokenIdentifier;
  @NonNull
  Nonce nonce;
  @NonNull
  TokenAttributes tokenAttributes;
  @NonNull
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultNftCreate();

  private PayloadData processPayloadData() {
    return PayloadData.fromString(format("%s@%s@%s@%s",
        ESDT_NFT_UPDATE_ATTRIBUTES_CALL,
        tokenIdentifier.getHex(),
        nonce.getHex(),
        tokenAttributes.getHex()));
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

}
