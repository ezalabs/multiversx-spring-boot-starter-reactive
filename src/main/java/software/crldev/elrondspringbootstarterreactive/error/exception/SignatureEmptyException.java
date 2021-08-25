package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class SignatureEmptyException extends RuntimeException {

    public SignatureEmptyException() {
        super(ErrorMessage.SIGNATURE_EMPTY.getValue());
    }
}
