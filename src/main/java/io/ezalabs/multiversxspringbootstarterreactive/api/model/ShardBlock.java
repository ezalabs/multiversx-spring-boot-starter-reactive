package io.ezalabs.multiversxspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * API response when querying for block shard data
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShardBlock {

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
  @JsonProperty("shard")
  Integer shard;
  @JsonProperty("accumulatedFees")
  BigInteger accumulatedFees;
  @JsonProperty("developerFees")
  BigInteger developerFees;
  @JsonProperty("status")
  String status;

}