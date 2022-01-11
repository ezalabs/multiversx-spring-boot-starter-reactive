package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

import static java.lang.String.format;

public class InvalidPemFileException extends RuntimeException {

    public InvalidPemFileException(Throwable t) {
        super(format(ErrorMessage.INVALID_PEM_FILE.getValue(), t.getMessage()));
    }
}
