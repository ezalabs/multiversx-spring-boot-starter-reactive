package software.crldev.multiversxspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigInteger;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import software.crldev.multiversxspringbootstarterreactive.domain.ApiModelToDomainConvertible;
import software.crldev.multiversxspringbootstarterreactive.domain.account.Address;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Balance;
import software.crldev.multiversxspringbootstarterreactive.domain.common.Nonce;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasLimit;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.GasPrice;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.Hash;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.PayloadData;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.Signature;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.Transaction;
import software.crldev.multiversxspringbootstarterreactive.domain.transaction.TransactionStatus;

/**
 * API response when querying for a transaction by hash
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionOnNetwork implements ApiModelToDomainConvertible<Transaction> {

  @JsonProperty("type")
  String type;
  @JsonProperty("nonce")
  Long nonce;
  @JsonProperty("round")
  Long round;
  @JsonProperty("epoch")
  Long epoch;
  @JsonProperty("value")
  BigInteger value;
  @JsonProperty("receiver")
  String receiver;
  @JsonProperty("sender")
  String sender;
  @JsonProperty("gasPrice")
  BigInteger gasPrice;
  @JsonProperty("gasLimit")
  BigInteger gasLimit;
  @JsonProperty("data")
  String data;
  @JsonProperty("signature")
  String signature;
  @JsonProperty("sourceShard")
  Long sourceShard;
  @JsonProperty("destinationShard")
  Long destinationShard;
  @JsonProperty("blockNonce")
  Integer blockNonce;
  @JsonProperty("blockHash")
  String blockHash;
  @JsonProperty("notarizedAtSourceInMetaNonce")
  Long notarizedAtSourceInMetaNonce;
  @JsonProperty("NotarizedAtSourceInMetaHash")
  String notarizedAtSourceInMetaHash;
  @JsonProperty("notarizedAtDestinationInMetaNonce")
  Long notarizedAtDestinationInMetaNonce;
  @JsonProperty("notarizedAtDestinationInMetaHash")
  String notarizedAtDestinationInMetaHash;
  @JsonProperty("miniblockType")
  String miniblockType;
  @JsonProperty("miniblockHash")
  String miniblockHash;
  @JsonProperty("timestamp")
  Long timestamp;
  @JsonProperty("status")
  String status;
  @JsonProperty("hyperblockNonce")
  Long hyperblockNonce;
  @JsonProperty("hyperblockHash")
  String hyperblockHash;
  @JsonProperty("receipt")
  Receipt receipt;
  @JsonProperty("smartContractResults")
  List<SmartContractResult> smartContractResults;
  @JsonProperty("logs")
  Logs logs;

  @Override
  public Transaction toDomainObject() {
    var transaction = new Transaction();
    transaction.setReceiver(Address.fromBech32(this.getReceiver()));
    transaction.setSender(Address.fromBech32(this.getSender()));
    transaction.setValue(Balance.fromNumber(this.getValue()));
    transaction.setNonce(Nonce.fromLong(this.getNonce()));
    transaction.setGasPrice(GasPrice.fromNumber(this.getGasPrice()));
    transaction.setGasLimit(GasLimit.fromNumber(this.getGasLimit()));
    transaction.setHash(Hash.fromString(this.getBlockHash()));
    transaction.setPayloadData(PayloadData.fromBase64Encoded(this.getData()));
    transaction.applySignature(Signature.fromHex(this.getSignature()));
    transaction.setStatus(TransactionStatus.fromString(this.getStatus()));
    return transaction;
  }

  @Builder
  @JsonDeserialize(builder = Logs.LogsBuilder.class)
  private static class Logs {

    @JsonProperty("address")
    String address;
    @JsonProperty("events")
    List<Event> events;

  }

  @Builder
  @JsonDeserialize(builder = Event.EventBuilder.class)
  private static class Event {

    @JsonProperty("address")
    String address;
    @JsonProperty("identifier")
    String identifier;
    @JsonProperty("topics")
    List<String> topics;

  }

}
