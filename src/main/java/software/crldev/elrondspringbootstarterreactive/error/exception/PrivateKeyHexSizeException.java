package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class PrivateKeyHexSizeException extends RuntimeException {

    public PrivateKeyHexSizeException(int size) {
        super(String.format(ErrorMessage.PRIVATE_KEY_LENGTH.getValue(), size));
    }
}
