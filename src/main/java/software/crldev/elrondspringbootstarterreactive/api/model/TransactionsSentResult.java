package software.crldev.elrondspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Map;

/**
 * API response sending multiple transactions for execution
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionsSentResult {

    @JsonProperty("numOfSentTxs")
    Integer numberOfSentTransactions;
    @JsonProperty("txsHashes")
    Map<String, String> transactionsHashes;

}
