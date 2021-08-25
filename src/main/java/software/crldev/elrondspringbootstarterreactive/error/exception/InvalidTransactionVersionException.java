package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class InvalidTransactionVersionException extends RuntimeException {

    public InvalidTransactionVersionException(int transactionId) {
        super(String.format(ErrorMessage.INVALID_TRANSACTION_VERSION.getValue(), transactionId));
    }
}
