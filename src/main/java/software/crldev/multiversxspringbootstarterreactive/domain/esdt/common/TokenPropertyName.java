package software.crldev.multiversxspringbootstarterreactive.domain.esdt.common;

import static software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage.TOKEN_PROPERTY;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import org.bouncycastle.util.encoders.Hex;

@Getter
public enum TokenPropertyName {

  CAN_FREEZE("canFreeze"),
  CAN_WIPE("canWipe"),
  CAN_PAUSE("canPause"),
  CAN_CHANGE_OWNER("canChangeOwner"),
  CAN_UPGRADE("canUpgrade"),
  CAN_ADD_SPECIAL_ROLES("canAddSpecialRoles"),
  CAN_TRANSFER_NFT_CREATE_ROLE("canTransferNFTCreateRole");

  private static final Map<String, TokenPropertyName> BY_VALUE = new HashMap<>();

  static {
    for (TokenPropertyName e : values()) {
      BY_VALUE.put(e.value, e);
    }
  }

  private final String hex;
  private final String value;

  TokenPropertyName(String value) {
    this.value = value;
    this.hex = Hex.toHexString(value.getBytes());
  }

  public static TokenPropertyName getByValue(String value) {
    return Optional.ofNullable(BY_VALUE.get(value))
        .orElseThrow(() -> new IllegalArgumentException(TOKEN_PROPERTY.getValue()));
  }
}
