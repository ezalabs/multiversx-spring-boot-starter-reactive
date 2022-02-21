package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTTransaction;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_TRANSFER_OWNERSHIP_CALL;

/**
 * Value object for ESDT ownership transfer
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTOwnershipTransfer implements ESDTTransaction {

    @NonNull
    TokenIdentifier tokenIdentifier;
    @NonNull
    Address targetAddress;
    @NonNull
    @Builder.Default
    GasLimit gasLimit = GasLimit.defaultEsdtIssuance();

    private PayloadData processPayloadData() {
        return PayloadData.fromString(format("%s@%s@%s",
                ESDT_TRANSFER_OWNERSHIP_CALL,
                tokenIdentifier.getHex(),
                targetAddress.getHex()));
    }

    @Override
    public TransactionRequest toTransactionRequest(Wallet wallet) {
        return TransactionRequest.builder()
                .receiverAddress(Address.fromBech32(ESDT_ISSUER_BECH32_ADDRESS))
                .data(processPayloadData())
                .value(Balance.zero())
                .gasLimit(gasLimit)
                .build();
    }

}
