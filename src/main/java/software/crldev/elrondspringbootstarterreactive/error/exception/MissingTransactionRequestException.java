package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class MissingTransactionRequestException extends RuntimeException {

    public MissingTransactionRequestException() {
        super(ErrorMessage.MISSING_TRANSACTIONS_REQUESTS.getValue());
    }
}
