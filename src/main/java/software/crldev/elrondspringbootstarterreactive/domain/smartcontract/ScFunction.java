package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;

/**
 * Value object for transaction PayloadData
 *
 * @author carlo_stanciu
 */
@Builder(toBuilder = true)
@Value
public class ScFunction {

    @NonNull
    Address smartContractAddress;
    @NonNull
    FunctionName functionName;
    @NonNull
    FunctionArgs args;
    Balance value;
    GasLimit gasLimit;

    /**
     * Retrieves an instance of PayloadData
     * containing the function name and arguments in the required format
     *
     * @return - an instance of PayloadData
     */
    public PayloadData getPayloadData() {
        return PayloadData.fromString(this.functionName + this.args.toString());
    }
}
