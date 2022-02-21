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
import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;
import software.crldev.elrondspringbootstarterreactive.interactor.transaction.TransactionRequest;

import static io.netty.util.internal.StringUtil.EMPTY_STRING;
import static java.lang.String.format;
import static software.crldev.elrondspringbootstarterreactive.config.constants.ESDTConstants.*;

/**
 * Value object for ESDT global operations
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTGlobalOp implements ESDTTransaction {

    @NonNull
    Type type;
    @NonNull
    TokenIdentifier tokenIdentifier;
    @NonNull
    @Builder.Default
    Address targetAddress = Address.zero();
    @NonNull
    @Builder.Default
    GasLimit gasLimit = GasLimit.defaultEsdtIssuance();

    private PayloadData processPayloadData() {
        return PayloadData.fromString(format("%s@%s%s",
                processType(),
                tokenIdentifier.getHex(),
                processAddressArg()
        ));
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

    private String processAddressArg() {
        if (type.equals(Type.PAUSE) || type.equals(Type.UNPAUSE))
            return EMPTY_STRING;
        return "@" + targetAddress.getHex();
    }

    private String processType() {
        switch (type) {
            case FREEZE:
                throwIfAddressEmpty();
                return ESDT_FREEZE_CALL;
            case UNFREEZE:
                throwIfAddressEmpty();
                return ESDT_UNFREEZE_CALL;
            case PAUSE:
                return ESDT_PAUSE_CALL;
            case UNPAUSE:
                return ESDT_UNPAUSE_CALL;
            case WIPE:
                throwIfAddressEmpty();
                return ESDT_WIPE_CALL;
            default:
                throw new IllegalArgumentException();
        }
    }

    private void throwIfAddressEmpty() {
        if (targetAddress.isZero())
            throw new IllegalArgumentException(ErrorMessage.ADDRESS_EMPTY.getValue());
    }

    public enum Type {PAUSE, UNPAUSE, FREEZE, UNFREEZE, WIPE}
}
