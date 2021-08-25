package software.crldev.elrondspringbootstarterreactive.api.model;

import software.crldev.elrondspringbootstarterreactive.domain.ApiModelToDomainConvertible;
import software.crldev.elrondspringbootstarterreactive.domain.account.Account;
import software.crldev.elrondspringbootstarterreactive.domain.account.Address;
import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigInteger;

/**
 * API response when querying for account info
 *
 * @author carlo_stanciu
 */
@Value
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AccountOnNetwork implements ApiModelToDomainConvertible<Account> {

    @JsonProperty("address")
    String address;
    @JsonProperty("balance")
    BigInteger balance;
    @JsonProperty("developerReward")
    BigInteger developerReward;
    @JsonProperty("nonce")
    Long nonce;
    @JsonProperty("username")
    String username;
    @JsonProperty("code")
    String code;
    @JsonProperty("codeHash")
    String codeHash;
    @JsonProperty("codeMetadata")
    String codeMetadata;
    @JsonProperty("ownerAddress")
    String ownerAddress;
    @JsonProperty("rootHash")
    String rootHash;

    @Override
    public Account toDomainObject() {
        var account = new Account();
        account.setAddress(Address.fromBech32(this.getAddress()));
        account.setNonce(Nonce.fromLong(this.getNonce()));
        account.setBalance(Balance.fromNumber(this.getBalance()));
        account.setCode(this.getCode());
        account.setUsername(this.getUsername());
        return account;
    }

}
