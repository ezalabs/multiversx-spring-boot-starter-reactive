package software.crldev.multiversxspringbootstarterreactive.domain.transaction;

import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;
import software.crldev.multiversxspringbootstarterreactive.error.exception.InvalidTransactionStatusException;

/**
 * Enum representing Transaction status values
 *
 * @author carlo_stanciu
 */
@AllArgsConstructor
@Getter
public enum TransactionStatus {

  PENDING("pending"),
  SUCCESS("success"),
  FAIL("fail"),
  INVALID("invalid"),
  UNKNOWN("unknown");

  private final String value;

  /**
   * Creates enum instance from String input
   *
   * @param value - String value of the status
   * @return - TransactionStatus instance
   */
  public static TransactionStatus fromString(String value) {
    var status = value.toLowerCase(Locale.ROOT);

    switch (status) {
      case "pending":
        return TransactionStatus.PENDING;
      case "success":
        return TransactionStatus.SUCCESS;
      case "fail":
        return TransactionStatus.FAIL;
      case "invalid":
        return TransactionStatus.INVALID;
      default:
        throw new InvalidTransactionStatusException(status);
    }
  }

}
