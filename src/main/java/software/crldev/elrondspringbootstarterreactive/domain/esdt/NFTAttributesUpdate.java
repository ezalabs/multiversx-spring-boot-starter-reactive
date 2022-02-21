package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.*;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import java.util.stream.Collectors;

import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NFT_CREATE_CALL;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_NFT_UPDATE_ATTRIBUTES_CALL;

/**
 * Value object for NFT attributes update
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class NFTAttributesUpdate implements ESDTTransaction {

    @NonNull
    TokenIdentifier tokenIdentifier;
    @NonNull
    Nonce nonce;
    @NonNull
    TokenAttributes tokenAttributes;
    @NonNull
    @Builder.Default
    GasLimit gasLimit = GasLimit.defaultNftCreate();

    private PayloadData processPayloadData() {
        return PayloadData.fromString(format("%s@%s@%s@%s",
                ESDT_NFT_UPDATE_ATTRIBUTES_CALL,
                tokenIdentifier.getHex(),
                nonce.getHex(),
                tokenAttributes.getHex()));
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
