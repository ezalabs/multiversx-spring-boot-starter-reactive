package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;

import static java.util.Objects.nonNull;

@Builder
@Value
public class ScQuery {

    @NonNull
    Address smartContractAddress;
    @NonNull
    FunctionName functionName;
    @NonNull
    FunctionArgs args;
    Address callerAddress;
    Balance value;

    public Sendable toSendable() {
        return Sendable.builder()
                .smartContractAddress(smartContractAddress.getBech32())
                .functionName(functionName.getValue())
                .args(args.getValue().toArray(new String[]{}))
                .callerAddress(nonNull(callerAddress) ? callerAddress.getBech32() : null)
                .value(nonNull(value) ? value.toString() : null)
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
