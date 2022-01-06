package software.crldev.elrondspringbootstarterreactive.domain.smartcontract;

import org.junit.jupiter.api.Test;
import software.crldev.elrondspringbootstarterreactive.api.model.ScQueryResult;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScQueryResultTest {

    List<String> encodedData = Arrays.asList("ZWxyb25k", "Y3JsZGV2");

    @Test
    void getData() {
        var result = ScQueryResult.builder()
                .data(encodedData)
                .code("ok")
                .message("success")
                .build();

        assertEquals(encodedData, result.getData());
    }
}