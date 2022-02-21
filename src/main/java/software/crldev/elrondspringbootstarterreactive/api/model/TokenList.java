package software.crldev.elrondspringbootstarterreactive.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.Set;

/**
 * part of API response used in ESDT queries
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TokenList {

    @JsonProperty("tokens")
    Set<String> tokens;

}
