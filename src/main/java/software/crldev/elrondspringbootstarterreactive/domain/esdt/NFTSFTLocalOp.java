package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTTransaction;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NFTSFT_ADD_CALL;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NFTSFT_BURN_CALL;

/**
 * Value object for NFT \ SFT local operations (add/burn quantity)
 *
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
