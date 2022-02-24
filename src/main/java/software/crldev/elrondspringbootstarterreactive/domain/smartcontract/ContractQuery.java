package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Value object for Smart Contract query
 *
 * @author carlo_stanciu
 */
@Builder
@Value
public class ContractQuery {

    @NonNull
    Address smartContractAddress;
    @NonNull
    FunctionName functionName;
    @NonNull
    @Builder.Default
    List<FunctionArg> args = Collections.emptyList();
    @Builder.Default
    Address callerAddress = Address.zero();
    @Builder.Default
    Balance value = Balance.zero();

    /**
     * Method used to create a Sendable representation of the query
     * used for submitting to the ContractInteractor
     *
     * @return - instance of SendableTransaction
     */
    public Sendable toSendable() {
        return Sendable.builder()
                .smartContractAddress(smartContractAddress.getBech32())
                .functionName(functionName.getValue())
                .args(args.stream().map(FunctionArg::getHex).collect(Collectors.toList()).toArray(String[]::new))
                .callerAddress(callerAddress.isZero() ? null : callerAddress.getBech32())
                .value(value.isZero() ? null : value.toString())
                .build();
    }

    @Value
    @Builder(access = AccessLevel.PRIVATE)
    public static class Sendable {

        @JsonProperty("scAddress")
        String smartContractAddress;
        @JsonProperty("funcName")
        String functionName;
        @JsonProperty("args")
        String[] args;
        @JsonProperty("caller")
        String callerAddress;
        @JsonProperty("value")
        String value;

    }

}
