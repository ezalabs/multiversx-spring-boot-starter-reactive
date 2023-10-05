package io.ezalabs.multiversxspringbootstarterreactive.domain.account;

import lombok.Data;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Balance;
import io.ezalabs.multiversxspringbootstarterreactive.domain.common.Nonce;

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
