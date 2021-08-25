package software.crldev.elrondspringbootstarterreactive.interactor.transaction;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;

/**
 * This class represents a Transaction in a sendable format
 * user for the TransactionInteractor
 */
@Value
@Builder
public class SendableTransaction {

    @JsonProperty("nonce")
    Long nonce;
    @JsonProperty("value")
    String value;
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
    @JsonProperty("chainID")
    String chainId;
    @JsonProperty("signature")
    String signature;
    @JsonProperty("version")
    Integer version;

}


