package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTTransaction;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.*;

/**
 * Value object for NFT \ SFT global operations
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class NFTSFTGlobalOp implements ESDTTransaction {

    @NonNull
    Type type;
    @NonNull
    TokenIdentifier tokenIdentifier;
    @NonNull
    Nonce nonce;
    @NonNull
    Address address;
    @NonNull
    @Builder.Default
    GasLimit gasLimit = GasLimit.defaultEsdtGlobalOp();

    private PayloadData processPayloadData() {
        return PayloadData.fromString(format("%s@%s@%s@%s",
                processType(),
                tokenIdentifier.getHex(),
                nonce.getHex(),
                address.getHex()));
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

    private String processType() {
        switch (type) {
            case FREEZE:
                return ESDT_NFT_FREEZE_CALL;
            case UNFREEZE:
                return ESDT_NFT_UNFREEZE_CALL;
            case WIPE:
                return ESDT_NFT_WIPE_CALL;
            default:
                throw new IllegalArgumentException();
        }
    }

    public enum Type {FREEZE, UNFREEZE, WIPE}
}
