package software.crldev.elrondspringbootstarterreactive.error.exception;

import software.crldev.elrondspringbootstarterreactive.error.ErrorMessage;

import java.math.BigInteger;

public class NegativeGasException extends RuntimeException {

    public NegativeGasException(BigInteger gasLimit) {
        super(String.format(ErrorMessage.NEGATIVE_GAS.getValue(), gasLimit));
    }
}
