package software.crldev.elrondspringbootstarterreactive.sender;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;

/**
 * Object used as a payload in the TransactionSender's methods
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class TransactionRequest {

    @NonNull
    Address receiverAddress;
    @NonNull
    Balance value;
    @NonNull
    PayloadData data;

}




