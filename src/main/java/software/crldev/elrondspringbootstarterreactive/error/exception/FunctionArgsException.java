package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class FunctionArgsException extends RuntimeException {

    public FunctionArgsException() {
        super(ErrorMessage.FUNCTION_ARGS_NULL.getValue());
    }
}
