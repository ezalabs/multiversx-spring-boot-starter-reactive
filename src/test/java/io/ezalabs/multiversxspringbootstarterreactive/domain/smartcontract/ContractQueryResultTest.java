package io.ezalabs.multiversxspringbootstarterreactive.domain.smartcontract;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.ezalabs.multiversxspringbootstarterreactive.api.model.ContractQueryResult;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;

class ContractQueryResultTest {

  List<String> encodedData = Arrays.asList("ZWxyb25k", "Y3JsZGV2");

  @Test
  void getData() {
    var result = ContractQueryResult.builder()
        .data(encodedData)
        .code("ok")
        .message("success")
        .build();

    assertEquals(encodedData, result.getData());
  }
}