package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class InvalidSentTransactionsException extends RuntimeException {

    public InvalidSentTransactionsException() {
        super(ErrorMessage.INVALID_SENT_TRANSACTIONS.getValue());
    }
}
