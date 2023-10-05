package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import io.ezalabs.multiversxspringbootstarterreactive.domain.account.Address;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common.TransferToken;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import io.ezalabs.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import io.ezalabs.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import io.ezalabs.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;
import io.ezalabs.multiversxspringbootstarterreactive.util.HexValidator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Value object for multi ESDT transfer
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTNFTMultiTransfer implements ESDTTransaction {

  @NonNull
  Address receiverAddress;
  @NonNull
  List<TransferToken> tokenList;

  private PayloadData processPayloadData() {
    return PayloadData.fromString(String.format("%s@%s@%s@%s",
        ESDTConstants.ESDT_MULTI_TRANSFER_CALL,
        receiverAddress.getHex(),
        HexValidator.processNumberHexArgument(Integer.toString(tokenList.size(), 16)),
        processTokenList()));
  }

  @Override
  public TransactionRequest toTransactionRequest(Wallet wallet) {
    return TransactionRequest.builder()
        .receiverAddress(wallet.getAddress())
        .data(processPayloadData())
        .value(Balance.zero())
        .gasLimit(GasLimit.multiEsdtTransfer(tokenList.size()))
        .build();
  }

  private String processTokenList() {
    return tokenList.stream()
        .map(t -> t.getIdentifier().getHex()
            + "@" + t.getNonce().getHex()
            + "@" + t.getAmount().getHex())
        .collect(Collectors.joining("@"));
  }
}
