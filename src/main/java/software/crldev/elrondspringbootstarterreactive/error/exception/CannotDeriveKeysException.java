package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class CannotDeriveKeysException extends RuntimeException {

    public CannotDeriveKeysException() {
        super(ErrorMessage.CANNOT_DERIVE_KEYS.getValue());
    }
}
