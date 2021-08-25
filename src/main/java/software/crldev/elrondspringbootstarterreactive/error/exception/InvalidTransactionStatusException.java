package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class InvalidTransactionStatusException extends RuntimeException {

    public InvalidTransactionStatusException(String status) {
        super(String.format(ErrorMessage.INVALID_BALANCE.getValue(), status));
    }
}
