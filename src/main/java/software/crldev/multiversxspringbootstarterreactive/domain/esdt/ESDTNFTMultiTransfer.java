package software.crldev.multiversxspringbootstarterreactive.domain.esdt;

import static java.lang.String.format;
import static software.crldev.multiversxspringbootstarterreactive.config.constants.ESDTConstants.ESDT_MULTI_TRANSFER_CALL;
import static software.crldev.multiversxspringbootstarterreactive.util.HexValidator.processNumberHexArgument;

import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.esdt.common.TransferToken;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.multiversxspringbootstarterreactive.interactor.transaction.TransactionRequest;

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
    return PayloadData.fromString(format("%s@%s@%s@%s",
        ESDT_MULTI_TRANSFER_CALL,
        receiverAddress.getHex(),
        processNumberHexArgument(Integer.toString(tokenList.size(), 16)),
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
