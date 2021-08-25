package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class CannotGenerateMnemonicException extends RuntimeException {

    public CannotGenerateMnemonicException() {
        super(ErrorMessage.CANNOT_GENERATE_MNEMONIC.getValue());
    }
}
