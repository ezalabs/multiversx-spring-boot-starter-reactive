package software.crldev.multiversxspringbootstarterreactive.api;

import static io.netty.util.internal.StringUtil.isNullOrEmpty;

import lombok.Data;
import software.crldev.multiversxspringbootstarterreactive.error.exception.ResponseException;

/**
 * Base API response received from the MultiversX proxy with generic based on response type
 *
 * @author carlo_stanciu
 */
@Data
public class ApiResponse<T> {

  private String error;
  private String code;
  private T data;

  /**
   * Method throws exception if the api response code is other than successful
   */
  public void throwIfError() {
    if (!isNullOrEmpty(error)) {
      throw new ResponseException(error);
    }

    if (!code.equals("successful")) {
      throw new ResponseException();
    }
  }

}
