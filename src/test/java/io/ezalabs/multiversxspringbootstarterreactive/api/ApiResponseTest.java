package io.ezalabs.multiversxspringbootstarterreactive.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.ezalabs.multiversxspringbootstarterreactive.error.ErrorMessage;
import io.ezalabs.multiversxspringbootstarterreactive.error.exception.ResponseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
    Assertions.assertEquals(ErrorMessage.RESPONSE_NOT_SUCCESSFUL.getValue(), ex2.getMessage());
  }
}