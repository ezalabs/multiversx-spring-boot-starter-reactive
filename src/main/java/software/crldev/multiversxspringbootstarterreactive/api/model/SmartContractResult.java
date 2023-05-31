package software.crldev.multiversxspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigInteger;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * part of API response when querying for transaction status
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SmartContractResult {

  @JsonProperty("hash")
  String hash;
  @JsonProperty("nonce")
  Long nonce;
  @JsonProperty("value")
  BigInteger value;
  @JsonProperty("receiver")
  String receiver;
  @JsonProperty("sender")
  String sender;
  @JsonProperty("data")
  String data;
  @JsonProperty("prevTxHash")
  String previousTransactionHash;
  @JsonProperty("originalTxHash")
  String originalTransactionHash;
  @JsonProperty("gasLimit")
  BigInteger gasLimit;
  @JsonProperty("gasPrice")
  BigInteger gasPrice;
  @JsonProperty("callType")
  Integer callType;

}