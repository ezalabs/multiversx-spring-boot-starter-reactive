package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.elrondspringbootstarterreactive.domain.transaction.PayloadData;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Value object for Smart Contract function
 *
 * @author carlo_stanciu
 */
@Builder(toBuilder = true)
@Value
public class ContractFunction {

    @NonNull
    Address smartContractAddress;
    @NonNull
    FunctionName functionName;
    @NonNull
    @Builder.Default
    List<FunctionArg> args = Collections.emptyList();
    @NonNull
    @Builder.Default
    Balance value = Balance.zero();
    @NonNull
    @Builder.Default
    GasLimit gasLimit = GasLimit.defaultSmartContractCall();

    /**
     * Retrieves an instance of Contract Function
     * containing the function name and arguments in the required format
     *
     * @return - an instance of PayloadData
     */
    public PayloadData getPayloadData() {
        return PayloadData.fromString(String.format("%s%s",
                functionName.getValue(),
                args.stream()
                        .map(p -> "@" + p.getHex())
                        .collect(Collectors.joining())));
    }
}
