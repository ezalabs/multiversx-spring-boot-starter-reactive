package software.crldev.elrondspringbootstarterreactive.api;

import lombok.Data;
import software.crldev.elrondspringbootstarterreactive.error.exception.ResponseException;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;

/**
 * Base API response received from the Elrond proxy
 * with generic based on response type
 *
 * @author carlo_stanciu
 */
@Data
public class ApiResponse<T> {

    private String error;
    private String code;
    private T data;

    /**
     * Method throws exception if the
     * api response code is other than successful
     */
    public void throwIfError() {
        if (!isNullOrEmpty(error))
            throw new ResponseException(error);

        if (!code.equals("successful"))
            throw new ResponseException();
    }

}
