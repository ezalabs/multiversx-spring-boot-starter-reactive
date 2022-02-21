package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.apache.logging.log4j.util.Strings;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTTransaction;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionArgs;
import software.crldev.elrondspringbootstarterreactive.domain.smartcontract.FunctionName;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import java.util.stream.Collectors;

import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_TRANSFER_CALL;

/**
 * Value object for ESDT transfer
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTTransfer implements ESDTTransaction {

    @NonNull
    Address receiverAddress;
    @NonNull
    TokenIdentifier tokenIdentifier;
    @NonNull
    Balance amount;
    @NonNull
    @Builder.Default
    FunctionName functionName = FunctionName.empty();
    @NonNull
    @Builder.Default
    FunctionArgs functionArgs = FunctionArgs.empty();
    @NonNull
    @Builder.Default
    GasLimit gasLimit = GasLimit.defaultEsdtTransfer();

    private PayloadData processPayloadData() {
        var functionCall = functionName.isEmpty() ? Strings.EMPTY
                : "@" + functionName.getHex() + functionArgs.getHex().stream()
                .map("@"::concat)
                .collect(Collectors.joining());

        return PayloadData.fromString(format("%s@%s@%s%s",
                ESDT_TRANSFER_CALL,
                tokenIdentifier.getHex(),
                amount.getHex(),
                functionCall));
    }

    @Override
    public TransactionRequest toTransactionRequest(Wallet wallet) {
        return TransactionRequest.builder()
                .receiverAddress(receiverAddress)
                .data(processPayloadData())
                .value(Balance.zero())
                .gasLimit(gasLimit)
                .build();
    }
}
