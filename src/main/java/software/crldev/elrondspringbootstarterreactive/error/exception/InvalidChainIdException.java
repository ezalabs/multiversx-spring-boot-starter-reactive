package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class InvalidChainIdException extends RuntimeException {

    public InvalidChainIdException(String chainId) {
        super(String.format(ErrorMessage.INVALID_CHAIN_ID.getValue(), chainId));
    }
}
