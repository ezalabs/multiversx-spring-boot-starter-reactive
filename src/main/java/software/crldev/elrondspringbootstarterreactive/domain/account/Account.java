package software.crldev.elrondspringbootstarterreactive.domain.account;

import software.crldev.elrondspringbootstarterreactive.domain.common.Balance;
import software.crldev.elrondspringbootstarterreactive.domain.common.Nonce;
import lombok.Data;

/**
 * Domain object for Account representation
 *
 * @author carlo_stanciu
 */
@Data
public class Account {

    private Address address = Address.zero();
    private Nonce nonce = Nonce.zero();
    private Balance balance = Balance.zero();
    private String code;
    private String username;

}
