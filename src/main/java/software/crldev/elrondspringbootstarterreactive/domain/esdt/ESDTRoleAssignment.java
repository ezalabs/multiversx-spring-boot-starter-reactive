package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.ESDTTransaction;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.ESDTSpecialRole;
import software.crldev.elrondspringbootstarterreactive.domain.esdt.common.TokenIdentifier;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.*;

/**
 * Value object for ESDT role assignment
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTRoleAssignment implements ESDTTransaction {

    @NonNull
    Type type;
    @NonNull
    TokenIdentifier tokenIdentifier;
    @NonNull
    Address address;
    @NonNull
    Set<ESDTSpecialRole> roles;
    @NonNull
    @Builder.Default
    GasLimit gasLimit = GasLimit.defaultEsdtIssuance();

    private PayloadData processPayloadData() {
        if (roles.isEmpty())
            throw new IllegalArgumentException(ErrorMessage.TOKEN_ROLE.getValue());

        return PayloadData.fromString(format("%s@%s@%s@%s",
                processType(),
                tokenIdentifier.getHex(),
                address.getHex(),
                roles.stream()
                        .map(ESDTSpecialRole::getHex)
                        .collect(Collectors.joining("@"))));
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
        return type.equals(Type.SET) ? ESDT_SET_ROLE_CALL : ESDT_UNSET_ROLE_CALL;
    }

    public enum Type { SET, UNSET }
}
