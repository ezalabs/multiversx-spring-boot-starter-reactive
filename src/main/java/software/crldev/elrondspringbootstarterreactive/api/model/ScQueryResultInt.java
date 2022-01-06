package software.crldev.elrondspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;

/**
 * API response when querying smart contract for integer value
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ScQueryResultInt {

    @JsonProperty("data")
    BigInteger value;

}
