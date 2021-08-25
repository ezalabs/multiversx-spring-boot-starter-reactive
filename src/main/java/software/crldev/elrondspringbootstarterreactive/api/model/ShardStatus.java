package software.crldev.elrondspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * API response when querying for shard status
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShardStatus {

    @JsonProperty("erd_current_round")
    long currentRound;
    @JsonProperty("erd_epoch_number")
    long epochNumber;
    @JsonProperty("erd_highest_final_nonce")
    long highestFinalNonce;
    @JsonProperty("erd_nonce")
    long nonce;
    @JsonProperty("erd_nonce_at_epoch_start")
    long nonceAtEpochStart;
    @JsonProperty("erd_nonces_passed_in_current_epoch")
    long noncesPassedInCurrentEpoch;
    @JsonProperty("erd_round_at_epoch_start")
    long roundAtEpochStart;
    @JsonProperty("erd_rounds_passed_in_current_epoch")
    long roundsPassedInCurrentEpoch;
    @JsonProperty("erd_rounds_per_epoch")
    long roundsPerEpoch;

}