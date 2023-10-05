package io.ezalabs.multiversxspringbootstarterreactive.domain.esdt.common;

import io.ezalabs.multiversxspringbootstarterreactive.config.constants.ESDTConstants;
import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import org.bouncycastle.util.encoders.Hex;

@Getter
public enum ESDTSpecialRole {

  ESDT_LOCAL_BURN(ESDTConstants.ESDT_BURN_ROLE),
  ESDT_LOCAL_MINT(ESDTConstants.ESDT_MINT_ROLE),

  ESDT_TRANSFER(ESDTConstants.ESDT_TRANSFER),

  ESDT_NFT_CREATE(ESDTConstants.ESDT_NFT_CREATE_ROLE),
  ESDT_NFT_BURN(ESDTConstants.ESDT_NFT_BURN_ROLE),
  ESDT_NFT_ADD_QUANTITY(ESDTConstants.ESDT_NFT_ADD_QUANTITY_ROLE),
  ESDT_NFT_UPDATE_ATTRIBUTES(ESDTConstants.ESDT_NFT_UPDATE_ATTRIBUTES),
  ESDT_NFT_ADD_URI(ESDTConstants.ESDT_NFT_ADD_URI);

  private static final Map<String, ESDTSpecialRole> BY_VALUE = new HashMap<>();

  static {
    for (ESDTSpecialRole e : values()) {
      BY_VALUE.put(e.value, e);
    }
  }

  private final String hex;
  private final String value;

  ESDTSpecialRole(String value) {
    this.value = value;
    this.hex = Hex.toHexString(value.getBytes());
  }

  public static ESDTSpecialRole getByValue(String value) {
    return Optional.ofNullable(BY_VALUE.get(value))
        .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.TOKEN_ROLE.getValue()));
  }
}
