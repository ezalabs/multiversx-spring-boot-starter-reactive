package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTTransaction;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_BURN_CALL;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_MINT_CALL;

/**
 * Value object for ESDT Local operations minting
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTLocalOp implements ESDTTransaction {

    @NonNull
    Type type;
    @NonNull
    TokenIdentifier tokenIdentifier;
    @NonNull
    Balance amount;
    @NonNull
    @Builder.Default
    GasLimit gasLimit = GasLimit.defaultEsdtLocalOp();

    private PayloadData processPayloadData() {
        return PayloadData.fromString(format("%s@%s@%s",
                processType(),
                tokenIdentifier.getHex(),
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
        return type.equals(Type.MINT) ? ESDT_MINT_CALL : ESDT_BURN_CALL;
    }

    public enum Type {MINT, BURN}
}
