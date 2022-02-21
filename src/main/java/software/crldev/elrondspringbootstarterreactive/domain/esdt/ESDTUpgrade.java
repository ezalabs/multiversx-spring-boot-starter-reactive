package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenProperty;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_ISSUER_BECH32_ADDRESS;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.ESDT_UPGRADE_CALL;

/**
 * Value object for ESDT upgrading
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTUpgrade implements ESDTTransaction {

    @NonNull
    TokenIdentifier tokenIdentifier;
    @Builder.Default
    GasLimit gasLimit = GasLimit.defaultEsdtIssuance();
    @Builder.Default
    Set<TokenProperty> properties = new HashSet<>();

    private PayloadData processPayloadData() {
        if (properties.isEmpty())
            throw new IllegalArgumentException(ErrorMessage.TOKEN_PROPERTY.getValue());

        return PayloadData.fromString(format("%s@%s%s",
                ESDT_UPGRADE_CALL,
                tokenIdentifier.getHex(),
                properties.stream()
                        .map(p -> "@" + p.getNameHex() + "@" + p.getValueHex())
                        .collect(Collectors.joining())));
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
