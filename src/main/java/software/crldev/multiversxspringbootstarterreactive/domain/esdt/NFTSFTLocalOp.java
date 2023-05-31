package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NFTSFT_ADD_CALL;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NFTSFT_BURN_CALL;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

/**
 * Value object for NFT \ SFT local operations (add/burn quantity)
 * <p>
 * !!! add quantity will only work for SFT token
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class NFTSFTLocalOp implements ESDTTransaction {

  @NonNull
  Type type;
  @NonNull
  TokenIdentifier tokenIdentifier;
  @NonNull
  Nonce nonce;
  @NonNull
  Balance amount;
  @NonNull
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultNftCreate();

  private PayloadData processPayloadData() {
    return PayloadData.fromString(format("%s@%s@%s@%s",
        processType(),
        tokenIdentifier.getHex(),
        nonce.getHex(),
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
    return type.equals(Type.ADD) ? ESDT_NFTSFT_ADD_CALL : ESDT_NFTSFT_BURN_CALL;
  }

  public enum Type {ADD, BURN}
}
