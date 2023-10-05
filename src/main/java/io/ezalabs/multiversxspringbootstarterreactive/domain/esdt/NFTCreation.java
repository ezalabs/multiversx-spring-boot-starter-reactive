package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.ESDTUri;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenAttributes;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenInitialSupply;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenName;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TokenRoyalties;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.Hash;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;
import java.math.BigInteger;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Value object for NFT creation
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class NFTCreation implements ESDTTransaction {

  @NonNull
  TokenIdentifier tokenIdentifier;
  @Builder.Default
  TokenInitialSupply initialQuantity = TokenInitialSupply.fromNumber(BigInteger.ONE);
  @NonNull
  TokenName nftName;
  @NonNull
  TokenRoyalties tokenRoyalties;
  @NonNull
  @Builder.Default
  Hash hash = Hash.empty();
  @NonNull
  TokenAttributes tokenAttributes;
  @NonNull
  Set<ESDTUri> uris;
  @NonNull
  @Builder.Default
  GasLimit gasLimit = GasLimit.defaultNftCreate();

  private PayloadData processPayloadData() {
    return PayloadData.fromString(String.format("%s@%s@%s@%s@%s@%s@%s@%s",
        ESDTConstants.ESDT_NFT_CREATE_CALL,
        tokenIdentifier.getHex(),
        initialQuantity.getHex(),
        nftName.getHex(),
        tokenRoyalties.getHex(),
        processHash(),
        tokenAttributes.getHex(),
        processUris()));
  }

  private String processHash() {
    return hash.isEmpty() ? "00" : hash.toString();
  }

  private String processUris() {
    if (uris.isEmpty()) {
      throw new IllegalArgumentException("List of URIs cannot be empty");
    }

    return uris.stream()
        .map(ESDTUri::getHex)
        .collect(Collectors.joining("@"));
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
