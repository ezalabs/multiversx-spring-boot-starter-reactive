package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class CannotConvertBitsException extends RuntimeException {

    public CannotConvertBitsException() {
        super(ErrorMessage.CANNOT_CONVERT_BITS.getValue());
    }
}
