package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class CannotDecodeBech32AddressException extends RuntimeException {

    public CannotDecodeBech32AddressException() {
        super(ErrorMessage.CANNOT_DECODE_BECH32_ADDRESS.getValue());
    }
}
