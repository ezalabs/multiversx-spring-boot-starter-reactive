package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class EmptyPayloadException extends RuntimeException {

    public EmptyPayloadException() {
        super(ErrorMessage.PAYLOAD.getValue());
    }
}
