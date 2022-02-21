package software.crldev.elrondspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;
import java.util.List;

/**
 * API response when querying for NFT data
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NFTData {

    @JsonProperty("attributes")
    String attributes;
    @JsonProperty("balance")
    BigInteger balance;
    @JsonProperty("creator")
    String creatorAddress;
    @JsonProperty("hash")
    String hash;
    @JsonProperty("name")
    String name;
    @JsonProperty("nonce")
    Long nonce;
    @JsonProperty("properties")
    String properties;
    @JsonProperty("royalties")
    String royalties;
    @JsonProperty("tokenIdentifier")
    String tokenIdentifier;
    @JsonProperty("uris")
    List<String> uris;

}