package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

import java.math.BigInteger;

public class InvalidBalanceException extends RuntimeException {

    public InvalidBalanceException(BigInteger balance) {
        super(String.format(ErrorMessage.INVALID_BALANCE.getValue(), balance));
    }
}
