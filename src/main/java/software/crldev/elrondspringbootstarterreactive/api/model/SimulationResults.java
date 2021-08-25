package software.crldev.elrondspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * API response when simulating transaction execution
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SimulationResults {

    @JsonProperty("receiverShard")
    ShardFromSimulatedTransaction receiverShard;
    @JsonProperty("senderShard")
    ShardFromSimulatedTransaction senderShard;
    @JsonProperty("scResults")
    List<SmartContractResult> smartContractResults;
    @JsonProperty("receipts")
    List<Receipt> receipts;

}
