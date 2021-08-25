package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class CannotCreateWalletException extends RuntimeException {

    public CannotCreateWalletException(Throwable e) {
        super(String.format(ErrorMessage.CANNOT_CREATE_WALLET.getValue(), e.getMessage()));
    }
}
