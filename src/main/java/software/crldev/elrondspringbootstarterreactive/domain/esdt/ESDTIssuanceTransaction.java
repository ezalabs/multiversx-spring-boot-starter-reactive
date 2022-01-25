package software.crldev.elrondspringbootstarterreactive.domain.esdt;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;

/**
 * Value object for transaction PayloadData
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ESDTIssuanceTransaction {

    @NonNull
    Address receiver;
    @NonNull
    TokenName tokenName;
    @NonNull
    TokenTicker tokenTicker;
    Balance value;
    GasLimit gasLimit;
}
