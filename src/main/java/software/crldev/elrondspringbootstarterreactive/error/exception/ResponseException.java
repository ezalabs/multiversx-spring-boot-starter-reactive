package software.crldev.elrondspringbootstarterreactive.error.exception;

public class ResponseException extends RuntimeException {

    public ResponseException(String error) {
        super(error);
    }

}
