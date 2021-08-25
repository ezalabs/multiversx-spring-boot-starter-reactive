package software.crldev.elrondspringbootstarterreactive.sender;

import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.elrondspringbootstarterreactive.domain.wallet.Wallet;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * Object used as a parameter in the TransactionSender's methods
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class TransactionRequest {

    @NonNull
    Wallet wallet;
    @NonNull
    Address receiverAddress;
    @NonNull
    Balance value;
    @NonNull
    PayloadData data;

}




