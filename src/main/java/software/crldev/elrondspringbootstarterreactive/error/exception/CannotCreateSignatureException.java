package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class CannotCreateSignatureException extends RuntimeException {

    public CannotCreateSignatureException(String value) {
        super(String.format(ErrorMessage.CANNOT_CREATE_SIGNATURE.getValue(), value));
    }
}
