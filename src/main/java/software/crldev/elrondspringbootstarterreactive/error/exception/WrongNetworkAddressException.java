package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

public class WrongNetworkAddressException extends RuntimeException {

    public WrongNetworkAddressException(String hrp) {
        super(String.format(ErrorMessage.WRONG_NETWORK_ADDRESS.getValue(), hrp));
    }
}
