package software.crldev.multiversxspringbootstarterreactive.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import software.crldev.multiversxspringbootstarterreactive.error.ErrorMessage;
import software.crldev.multiversxspringbootstarterreactive.error.exception.ResponseException;

class ApiResponseTest {

  @Test
  void throwIfError() {
    var apiResponse = new ApiResponse<>();
    var expectedErrMsg = "failed for some reason";
    apiResponse.setError(expectedErrMsg);

    var ex = assertThrows(ResponseException.class, apiResponse::throwIfError);
    assertEquals(expectedErrMsg, ex.getMessage());

    apiResponse.setError("");
    apiResponse.setCode("notSuccessful");

    var ex2 = assertThrows(ResponseException.class, apiResponse::throwIfError);
    assertEquals(ErrorMessage.RESPONSE_NOT_SUCCESSFUL.getValue(), ex2.getMessage());
  }
}