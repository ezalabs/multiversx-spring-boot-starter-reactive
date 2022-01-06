package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class FunctionNameException extends RuntimeException {

    public FunctionNameException() {
        super(ErrorMessage.FUNCTION_NAME.getValue());
    }
}
