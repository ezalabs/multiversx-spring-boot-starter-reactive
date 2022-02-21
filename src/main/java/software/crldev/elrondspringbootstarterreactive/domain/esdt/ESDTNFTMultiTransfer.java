package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTTransaction;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TransferToken;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_MULTI_TRANSFER_CALL;
import static software.crldev.elrondspringbootstarterreactive.util.HexValidator.processNumberHexArgument;

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
