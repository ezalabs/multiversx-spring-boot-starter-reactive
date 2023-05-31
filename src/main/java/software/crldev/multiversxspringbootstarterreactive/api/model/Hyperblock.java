package software.crldev.multiversxspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * API response when querying for hyperblock data
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Hyperblock {

  @JsonProperty("nonce")
  Long nonce;
  @JsonProperty("round")
  Long round;
  @JsonProperty("hash")
  String hash;
  @JsonProperty("prevBlockHash")
  String previousBlockHash;
  @JsonProperty("epoch")
  Long epoch;
  @JsonProperty("shardBlocks")
  List<ShardFromHyperblock> shardBlocks;
  @JsonProperty("accumulatedFees")
  BigInteger accumulatedFees;
  @JsonProperty("developerFees")
  BigInteger developerFees;
  @JsonProperty("accumulatedFeesInEpoch")
  BigInteger accumulatedFeesInEpoch;
  @JsonProperty("developerFeesInEpoch")
  BigInteger developerFeesInEpoch;
  @JsonProperty("status")
  String status;

}