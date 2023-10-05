package io.ezalabs.multiversxspringbootstarterreactive.domain.transaction;

import io.ezalabs.multiversxspringbootstarterreactive.error.exception.InvalidTransactionStatusException;
import java.util.Locale;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
