package software.crldev.multiversxspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * API response when querying for hyperblock shard data
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ShardFromHyperblock {

  @JsonProperty("hash")
  String hash;
  @JsonProperty("nonce")
  Long nonce;
  @JsonProperty("round")
  Long round;
  @JsonProperty("shard")
  Integer shard;

}