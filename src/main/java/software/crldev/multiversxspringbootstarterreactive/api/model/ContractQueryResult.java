package software.crldev.multiversxspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

/**
 * part of API response used in smart contract query results
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ContractQueryResult {

  @JsonProperty("returnData")
  List<String> data;
  @JsonProperty("returnCode")
  String code;
  @JsonProperty("returnMessage")
  String message;

}
