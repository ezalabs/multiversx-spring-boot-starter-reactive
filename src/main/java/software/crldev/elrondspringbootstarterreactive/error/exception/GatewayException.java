package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class GatewayException extends RuntimeException {

    public GatewayException() {
        super(ErrorMessage.INVALID_GATEWAY.getValue());
    }
}
