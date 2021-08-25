package software.crldev.elrondspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.Instant;

/**
 * API response when querying for node status
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NodeHeartbeatStatus {

    @JsonProperty("timeStamp")
    Instant timestamp;
    @JsonProperty("publicKey")
    String publicKey;
    @JsonProperty("versionNumber")
    String versionNumber;
    @JsonProperty("nodeDisplayName")
    String nodeDisplayName;
    @JsonProperty("identity")
    String identity;
    @JsonProperty("totalUpTimeSec")
    Long totalUpTimeSeconds;
    @JsonProperty("totalDownTimeSec")
    Long totalDownTimeSeconds;
    @JsonProperty("maxInactiveTime")
    String maxInactiveTime;
    @JsonProperty("receivedShardID")
    Long receivedShardId;
    @JsonProperty("computedShardID")
    Long computedShardId;
    @JsonProperty("peerType")
    String peerType;
    @JsonProperty("isActive")
    Boolean isActive;
    @JsonProperty("nonce")
    Long nonce;
    @JsonProperty("numInstances")
    Long numOfInstances;

}