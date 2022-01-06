package software.crldev.elrondspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

/**
 * part of API response used in smart contract query results
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ScQueryResult {

    @JsonProperty("returnData")
    List<String> data;
    @JsonProperty("returnCode")
    String code;
    @JsonProperty("returnMessage")
    String message;

}
