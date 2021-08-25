package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class InvalidNonceException extends RuntimeException {

    public InvalidNonceException(long nonce) {
        super(String.format(ErrorMessage.INVALID_NONCE.getValue(), nonce));
    }
}
